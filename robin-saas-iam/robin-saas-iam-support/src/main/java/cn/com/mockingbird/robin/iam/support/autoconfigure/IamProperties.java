package cn.com.mockingbird.robin.iam.support.autoconfigure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static cn.com.mockingbird.robin.iam.support.autoconfigure.IamProperties.IAM_PROPERTIES_PREFIX;

/**
 * IAM 配置属性
 *
 * @author zhaopeng
 * @date 2023/12/6 16:37
 **/
@Data
@ConfigurationProperties(prefix = IAM_PROPERTIES_PREFIX)
public class IamProperties {

    public static final String IAM_PROPERTIES_PREFIX = "iam";

    @NestedConfigurationProperty
    private final Server server = new Server();
    @NestedConfigurationProperty
    private final Audit audit = new Audit();
    @NestedConfigurationProperty
    private final User user = new User();
    @NestedConfigurationProperty
    private final Security security = new Security();

    @Getter
    @Setter
    public static class Server {
        private String adminPublicBaseUrl;
        private String portalPublicBaseUrl;
        private String synchronizerPublicBaseUrl;
        private String openApiPublicBaseUrl;
    }

    @Getter
    @Setter
    public static class Audit {
        private String indexPrefix = "iam-audit-";
    }

    @Getter
    @Setter
    public static class User {
        private String indexPrefix = "iam-user-";
    }

    @Getter
    @Setter
    public static class Security {

        private Jump jump = new Jump();

        @Getter
        @Setter
        public static class Jump {
            private String defaultRedirectUrl;
        }
    }
}
