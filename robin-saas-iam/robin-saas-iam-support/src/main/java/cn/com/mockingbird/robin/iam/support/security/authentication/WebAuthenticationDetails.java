package cn.com.mockingbird.robin.iam.support.security.authentication;

import cn.com.mockingbird.robin.iam.support.geo.GeoLocation;
import cn.com.mockingbird.robin.iam.support.web.useragent.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 与 Web 身份验证请求相关的选定 HTTP 详细信息的持有
 *
 * @author zhaopeng
 * @date 2023/12/6 20:09
 **/
@Getter
@Setter
public class WebAuthenticationDetails extends org.springframework.security.web.authentication.WebAuthenticationDetails {

    @Serial
    private static final long serialVersionUID = -5375714227808409295L;
    private final UserAgent userAgent;
    private final GeoLocation geoLocation;
    private AuthenticationProvider authenticationProvider;
    private LocalDateTime authenticationTime;

    public WebAuthenticationDetails(HttpServletRequest request, UserAgent userAgent, GeoLocation geoLocation) {
        super(request);
        this.userAgent = userAgent;
        this.geoLocation = geoLocation;
        this.authenticationTime = LocalDateTime.now();
    }

    public WebAuthenticationDetails(String remoteAddress, String sessionId, UserAgent userAgent, GeoLocation geoLocation, AuthenticationProvider authenticationProvider, LocalDateTime authenticationTime) {
        super(remoteAddress, sessionId);
        this.userAgent = userAgent;
        this.geoLocation = geoLocation;
        this.authenticationProvider = authenticationProvider;
        this.authenticationTime = authenticationTime;
    }

}
