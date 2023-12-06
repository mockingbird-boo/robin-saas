package cn.com.mockingbird.robin.iam.support.security.redirect;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 登录重定向参数过滤器
 *
 * @author zhaopeng
 * @date 2023/12/6 1:01
 **/
public class LoginRedirectParameterFilter extends OncePerRequestFilter {

    private final RedirectCache redirectCache = new HttpSessionRedirectCache();

    private final RequestMatcher requestMatcher;

    public LoginRedirectParameterFilter(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            redirectCache.saveRedirect(request, response, RedirectCache.RedirectType.PARAMETER);
        }
        filterChain.doFilter(request, response);
    }

}
