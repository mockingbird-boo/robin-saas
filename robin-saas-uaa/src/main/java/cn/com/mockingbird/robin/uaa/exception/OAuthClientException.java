package cn.com.mockingbird.robin.uaa.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * OAuth 客户端异常
 *
 * @author zhaopeng
 * @date 2023/12/2 2:01
 **/
@SuppressWarnings("serial")
public class OAuthClientException extends OAuth2AuthenticationException {

    public OAuthClientException(String message) {
        super(new OAuth2Error(message), message);
    }

    public OAuthClientException(String msg, Throwable cause) {
        super(new OAuth2Error(msg), cause);
    }

}
