package cn.com.mockingbird.robin.iam.support.security.redirect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 重定向缓存
 *
 * @author zhaopeng
 * @date 2023/12/6 1:03
 **/
public interface RedirectCache {

    String WEB_SECURITY_SAVED_REDIRECT = "WEB_SECURITY_SAVED_REDIRECT";

    void saveRedirect(HttpServletRequest request, HttpServletResponse response, RedirectType type);

    Redirect getRedirect(HttpServletRequest request, HttpServletResponse response);

    void removeRedirect(HttpServletRequest request, HttpServletResponse response);

    public static enum RedirectType {
        PARAMETER,
        REQUEST;

        private RedirectType() {
        }
    }
}
