package cn.com.mockingbird.robin.iam.support.web.useragent;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户代理
 *
 * @author zhaopeng
 * @date 2023/12/5 18:48
 **/
@Getter
@Setter
@AllArgsConstructor
public class UserAgent implements Serializable {

    @Serial
    private static final long serialVersionUID = 3158752211285766160L;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 浏览器类型
     */
    private String browserType;

    /**
     * 浏览器主流版本
     */
    private String browserMajorVersion;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 平台
     */
    private String platform;

    /**
     * 平台版本
     */
    private String platformVersion;

    /**
     * 渲染引擎
     */
    private String renderingEngineMaker;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static UserAgentBuilder builder() {
        return new UserAgentBuilder();
    }

    /**
     * 用户代理 Builder
     */
    public static class UserAgentBuilder {
        private String browser;
        private String browserType;
        private String browserMajorVersion;
        private String deviceType;
        private String platform;
        private String platformVersion;
        private String renderingEngineMaker;

        UserAgentBuilder() {
        }

        public UserAgentBuilder browser(final String browser) {
            this.browser = browser;
            return this;
        }

        public UserAgentBuilder browserType(final String browserType) {
            this.browserType = browserType;
            return this;
        }

        public UserAgentBuilder browserMajorVersion(final String browserMajorVersion) {
            this.browserMajorVersion = browserMajorVersion;
            return this;
        }

        public UserAgentBuilder deviceType(final String deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public UserAgentBuilder platform(final String platform) {
            this.platform = platform;
            return this;
        }

        public UserAgentBuilder platformVersion(final String platformVersion) {
            this.platformVersion = platformVersion;
            return this;
        }

        public UserAgentBuilder renderingEngineMaker(final String renderingEngineMaker) {
            this.renderingEngineMaker = renderingEngineMaker;
            return this;
        }

        public UserAgent build() {
            return new UserAgent(this.browser, this.browserType, this.browserMajorVersion, this.deviceType, this.platform, this.platformVersion, this.renderingEngineMaker);
        }

        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }
}
