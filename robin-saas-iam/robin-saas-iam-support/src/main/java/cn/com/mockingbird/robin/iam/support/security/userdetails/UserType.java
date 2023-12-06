package cn.com.mockingbird.robin.iam.support.security.userdetails;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户类型
 *
 * @author zhaopeng
 * @date 2023/12/6 0:25
 **/
@Getter
@Setter
public class UserType implements Serializable {

    @Serial
    private static final long serialVersionUID = 3538011567356397885L;

    public static final UserType ADMIN = new UserType("admin", "管理员");
    public static final UserType USER = new UserType("user", "用户");
    public static final UserType DEVELOPER = new UserType("developer", "开发者");
    public static final UserType UNKNOWN = new UserType("unknown", "未知");

    /**
     * 类型
     */
    private String type;

    /**
     * 类型名称
     */
    private String name;

    public UserType() {

    }

    public UserType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            UserType userType = (UserType) obj;
            return (new EqualsBuilder()).append(this.type, userType.type).append(this.name, userType.name).isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.type).append(this.name).toHashCode();
    }
}
