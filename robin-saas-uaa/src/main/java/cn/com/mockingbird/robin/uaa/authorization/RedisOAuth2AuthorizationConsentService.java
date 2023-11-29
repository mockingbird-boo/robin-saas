package cn.com.mockingbird.robin.uaa.authorization;

import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * OAuth2AuthorizationConsentService Redis 实现
 *
 * @author zhaopeng
 * @date 2023/11/27 18:49
 **/
public class RedisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {

    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {

    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return null;
    }

}
