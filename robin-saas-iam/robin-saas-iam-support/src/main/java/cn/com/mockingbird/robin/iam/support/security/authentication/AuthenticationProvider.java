package cn.com.mockingbird.robin.iam.support.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 认证信息提供者
 *
 * @author zhaopeng
 * @date 2023/12/6 20:09
 **/
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationProvider implements Serializable {

    @Serial
    private static final long serialVersionUID = 2954230958165163658L;

    private String type;

    private String name;

    public static final AuthenticationProvider USERNAME_PASSWORD = new AuthenticationProvider("username_password", "用户名密码认证");

}
