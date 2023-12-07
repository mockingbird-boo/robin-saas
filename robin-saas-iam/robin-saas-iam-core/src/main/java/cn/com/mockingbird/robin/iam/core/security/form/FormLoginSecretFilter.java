package cn.com.mockingbird.robin.iam.core.security.form;

import cn.com.mockingbird.robin.iam.common.constant.AuthorizeConstants;
import cn.com.mockingbird.robin.iam.support.enums.SecretType;
import cn.com.mockingbird.robin.iam.support.result.ApiRestResult;
import cn.com.mockingbird.robin.iam.support.util.AesUtils;
import cn.com.mockingbird.robin.iam.support.util.HttpResponseUtils;
import cn.com.mockingbird.robin.iam.support.web.servlet.ParameterRequestWrapper;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static cn.com.mockingbird.robin.iam.support.exception.ExceptionStatus.EX900005;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;

/**
 * 密钥过滤器
 *
 * @author zhaopeng
 * @date 2023/12/7 3:13
 **/
public class FormLoginSecretFilter extends OncePerRequestFilter {

    private final RequestMatcher requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(AuthorizeConstants.FORM_LOGIN_API, HttpMethod.POST.name());

    /**
     * 请求是否需要当前过滤器进行处理
     *
     * @param request 请求实例
     * @return true - 是
     */
    protected boolean requiresAuthentication(HttpServletRequest request) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (requiresAuthentication(request)) {
            // 获取加密密码
            String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
            try {
                // 拿到秘钥，解密
                String secret = (String) request.getSession().getAttribute(SecretType.LOGIN.getKey());
                password = AesUtils.decrypt(password, secret);
            } catch (Exception exception) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                HttpResponseUtils.flushResponse(response,
                        JSONObject.toJSONString(ApiRestResult.builder()
                                .status(EX900005.getCode())
                                .message(EX900005.getMessage())
                                .build())
                );
                return;
            }
            ParameterRequestWrapper wrapper = new ParameterRequestWrapper(request);
            wrapper.addParameter(SPRING_SECURITY_FORM_PASSWORD_KEY, password);
            filterChain.doFilter(wrapper, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

}
