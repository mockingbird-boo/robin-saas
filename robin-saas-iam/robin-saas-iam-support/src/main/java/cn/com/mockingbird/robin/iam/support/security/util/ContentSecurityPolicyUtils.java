package cn.com.mockingbird.robin.iam.support.security.util;

import com.shapesecurity.salvation2.Policy;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * 内容安全策略工具类
 *
 * @author zhaopeng
 * @date 2023/12/7 3:01
 **/
@UtilityClass
public class ContentSecurityPolicyUtils {

    public Policy parse(String serialized) {
        return Policy.parseSerializedCSP(serialized, (severity, message, directiveIndex, valueIndex) -> {
            throw new ContentSecurityPolicyPolicyException(new PolicyError(severity, message, directiveIndex, valueIndex));
        });
    }

    @Getter
    public class ContentSecurityPolicyPolicyException extends RuntimeException {

        private final PolicyError error;

        public ContentSecurityPolicyPolicyException(PolicyError error) {
            this.error = error;
        }

    }

    public record PolicyError(Policy.Severity severity, String message, int directiveIndex, int valueIndex) {
        public PolicyError(Policy.Severity severity, String message, int directiveIndex, int valueIndex) {
            this.severity = severity;
            this.message = message;
            this.directiveIndex = directiveIndex;
            this.valueIndex = valueIndex;
        }

        public Policy.Severity severity() {
            return this.severity;
        }

        public String message() {
            return this.message;
        }

        public int directiveIndex() {
            return this.directiveIndex;
        }

        public int valueIndex() {
            return this.valueIndex;
        }
    }

}
