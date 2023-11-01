package cn.com.mockingbird.robin.api.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * HttpServletRequest 的包装类
 * <p>
 * 用于将封装请求流，并重写了其中两个方法
 * <p>
 * 装饰者模式，通过继承 {@link HttpServletRequestWrapper}，可以完成对 {@link HttpServletRequest} 的替换
 *
 * @author zhaopeng
 * @date 2023/10/31 1:54
 **/
@Getter
@Setter
public class HttpServletRequestBodyWrapper extends HttpServletRequestWrapper {

    private String requestBody;

    public HttpServletRequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = new String(StreamUtils.copyToByteArray(request.getInputStream()), StandardCharsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 重写 getInputStream() 方法，从 requestBody 中获取请求参数
     * @return ServletInputStream 实例
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }
}
