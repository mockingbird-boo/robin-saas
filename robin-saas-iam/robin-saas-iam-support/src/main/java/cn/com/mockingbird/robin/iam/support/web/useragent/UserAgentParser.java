package cn.com.mockingbird.robin.iam.support.web.useragent;

import com.blueconic.browscap.BrowsCapField;
import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentService;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * 用户代理解析器
 *
 * @author zhaopeng
 * @date 2023/12/5 18:39
 **/
public final class UserAgentParser {

    private static volatile com.blueconic.browscap.UserAgentParser userAgentParser;

    public static final String USER_AGENT_HEADER = "User-Agent";

    private UserAgentParser () {

    }

    /**
     * 获取用户代理解析器
     */
    public static com.blueconic.browscap.UserAgentParser getUserAgentParser() {
        try {
            if (userAgentParser == null) {
                synchronized (UserAgentService.class) {
                    if (userAgentParser == null) {
                        userAgentParser = (new UserAgentService()).loadParser();
                    }
                }
            }
            return userAgentParser;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回用户代理
     * @param request 请求实例
     * @return 用户代理
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        Capabilities parse = getUserAgentParser().parse(request.getHeader(USER_AGENT_HEADER));
        String browser = parse.getBrowser();
        String browserType = parse.getBrowserType();
        String browserMajorVersion = parse.getBrowserMajorVersion();
        String deviceType = parse.getDeviceType();
        String platform = parse.getPlatform();
        String platformVersion = parse.getPlatformVersion();
        String renderingEngineMaker = parse.getValue(BrowsCapField.RENDERING_ENGINE_MAKER);
        return UserAgent.builder()
                .browser(browser)
                .browserType(browserType)
                .browserMajorVersion(browserMajorVersion)
                .deviceType(deviceType)
                .platform(platform)
                .platformVersion(platformVersion)
                .renderingEngineMaker(renderingEngineMaker)
                .build();
    }

}
