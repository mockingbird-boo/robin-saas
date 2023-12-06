package cn.com.mockingbird.robin.iam.support.security.web;

import cn.com.mockingbird.robin.iam.support.util.HttpRequestUtils;
import cn.com.mockingbird.robin.iam.support.util.IpUtils;
import cn.com.mockingbird.robin.iam.support.web.log.LogInfo;
import cn.com.mockingbird.robin.iam.support.web.useragent.UserAgentParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 由 ExceptionTranslationFilter 使用来启动身份验证方案。
 *
 * @see ExceptionTranslationFilter
 * @author zhaopeng
 * @date 2023/12/5 3:01
 **/
public abstract class AbstractAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractAuthenticationEntryPoint() {

    }

    /**
     * 开始验证方案。
     * 在调用此方法之前，ExceptionTranslationFilter 将使用请求的目标 URL 填充名为
     * AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY 的
     * HttpSession 属性。
     * 实现应根据需要修改 ServletResponse 上的标头以开始身份验证过程。
     * @param request 导致 AuthenticationException
     * @param response 以便用户代理可以开始身份验证
     * @param authException 认证异常
     * @throws IOException IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LogInfo logInfo = new LogInfo();
        logInfo.setUserAgent(UserAgentParser.getUserAgent(request));
        logInfo.setIp(IpUtils.getIpAddr(request));
        logInfo.setResult(authException.getMessage());
        logInfo.setRequestUrl(request.getRequestURL().toString());
        logInfo.setBody(IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8));
        logInfo.setMethod(request.getMethod());
        logInfo.setHeaders(HttpRequestUtils.getRequestHeaders(request));
        logInfo.setSuccess(false);
        logger.error(logInfo.toString());
    }
}
