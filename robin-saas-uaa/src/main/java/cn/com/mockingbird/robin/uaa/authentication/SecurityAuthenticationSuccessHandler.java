package cn.com.mockingbird.robin.uaa.authentication;

import cn.com.mockingbird.robin.uaa.userdetails.SecurityUser;
import cn.hutool.core.map.MapUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 安全认证通过处理器
 *
 * @author zhaopeng
 * @date 2023/12/1 22:42
 **/
@Slf4j
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthenticationToken = (OAuth2AccessTokenAuthenticationToken) authentication;
        Map<String, Object> additionalParameters = accessTokenAuthenticationToken.getAdditionalParameters();
        if (MapUtil.isNotEmpty(additionalParameters)) {
            SecurityUser securityUser = (SecurityUser) additionalParameters.get("user_info");
            if (log.isDebugEnabled()) {
                log.debug("用户-[{}]登录成功", securityUser.getName());
            }
            SecurityContextHolder.getContext().setAuthentication(accessTokenAuthenticationToken);
            // TODO 异步记录登录日志
        }
        sendAccessToken(request, response, accessTokenAuthenticationToken);
    }

    private void sendAccessToken(HttpServletRequest request, HttpServletResponse response, OAuth2AccessTokenAuthenticationToken accessTokenAuthentication) {
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue()).tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }
        OAuth2AccessTokenResponse tokenResponse = builder.build();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        SecurityContextHolder.clearContext();


    }

}
