package cn.com.mockingbird.robin.iam.support.security.authentication;

import cn.com.mockingbird.robin.iam.support.geo.GeoLocation;
import cn.com.mockingbird.robin.iam.support.geo.GeoLocationService;
import cn.com.mockingbird.robin.iam.support.util.IpUtils;
import cn.com.mockingbird.robin.iam.support.web.useragent.UserAgentParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.util.Assert;

/**
 * web 认证源
 *
 * @author zhaopeng
 * @date 2023/12/6 20:24
 **/
public class WebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    private final GeoLocationService geoLocationService;

    public WebAuthenticationDetailsSource(GeoLocationService geoLocationService) {
        Assert.notNull(geoLocationService, "geoLocationService requires not null");
        this.geoLocationService = geoLocationService;
    }

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        GeoLocation geoLocation = this.geoLocationService.getGeoLocation(IpUtils.getIpAddr(context));
        return new WebAuthenticationDetails(context, UserAgentParser.getUserAgent(context), geoLocation);
    }
}
