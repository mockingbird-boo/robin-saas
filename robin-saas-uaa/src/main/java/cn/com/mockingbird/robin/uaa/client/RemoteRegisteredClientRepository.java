package cn.com.mockingbird.robin.uaa.client;

import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.common.util.response.ResponseData;
import cn.com.mockingbird.robin.uaa.exception.OAuthClientException;
import cn.com.mockingbird.robin.upm.api.common.SecurityConstants;
import cn.com.mockingbird.robin.upm.api.entity.Client;
import cn.com.mockingbird.robin.upm.api.fegin.RemoteClientService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * 客户端信息查询实现
 *
 * @author zhaopeng
 * @date 2023/11/27 18:42
 **/
@RequiredArgsConstructor
public class RemoteRegisteredClientRepository implements RegisteredClientRepository {

    /**
     * 刷新令牌有效期 30 天
     */
    private final static int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期 12 小时
     */
    private final static int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;


    private final RemoteClientService remoteClientService;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        ResponseData<Client> clientResponse = remoteClientService.getClientById(clientId);
        if (ResponseData.hasData(clientResponse)) {
            Client client = clientResponse.getData();
            RegisteredClient.Builder builder = RegisteredClient.withId(client.getClientId())
                    .clientId(client.getClientId())
                    .clientSecret(SecurityConstants.NOOP + client.getClientSecret())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

            for (String authorizedGrantType : client.getAuthorizedGrantTypes()) {
                builder.authorizationGrantType(new AuthorizationGrantType(authorizedGrantType));

            }
            // 回调地址
            Optional.ofNullable(client.getRedirectUri())
                    .ifPresent(redirectUri -> Arrays.stream(redirectUri.split(Standard.Str.COMMA))
                            .filter(StringUtils::isNotBlank)
                            .forEach(builder::redirectUri));

            // scope
            Optional.ofNullable(client.getScope())
                    .ifPresent(scope -> Arrays.stream(scope.split(Standard.Str.COMMA))
                            .filter(StringUtils::isNotBlank)
                            .forEach(builder::scope));

            return builder
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                            .accessTokenTimeToLive(Duration.ofSeconds(
                                    Optional.ofNullable(client.getAccessTokenValidity()).orElse(ACCESS_TOKEN_VALIDITY_SECONDS)))
                            .refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(client.getRefreshTokenValidity())
                                    .orElse(REFRESH_TOKEN_VALIDITY_SECONDS)))
                            .build())
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(!client.getAutoApprove())
                            .build())
                    .build();
        } else {
            throw new OAuthClientException("客户端不存在");
        }
    }

}
