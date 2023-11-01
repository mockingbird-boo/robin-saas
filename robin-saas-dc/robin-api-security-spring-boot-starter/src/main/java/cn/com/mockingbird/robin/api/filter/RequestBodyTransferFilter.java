package cn.com.mockingbird.robin.api.filter;

import cn.com.mockingbird.robin.api.wrapper.HttpServletRequestBodyWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 请求体转换过滤器
 *
 * @author zhaopeng
 * @date 2023/11/2 0:04
 **/
public class RequestBodyTransferFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 使用装饰者替换 HttpServletRequest
        HttpServletRequestBodyWrapper httpServletRequestBodyWrapper = new HttpServletRequestBodyWrapper(request);
        filterChain.doFilter(httpServletRequestBodyWrapper, response);
    }

}
