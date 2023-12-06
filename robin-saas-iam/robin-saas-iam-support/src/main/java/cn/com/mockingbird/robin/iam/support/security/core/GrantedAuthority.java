package cn.com.mockingbird.robin.iam.support.security.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.io.Serial;

/**
 * 授予的权限
 *
 * @author zhaopeng
 * @date 2023/12/6 2:10
 **/
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 715941496715565950L;

    private final String authority;

    @Getter
    @Setter
    private String type;

    public GrantedAuthority(String authority, String type) {
        Assert.notNull(authority, "authority requires not null");
        Assert.notNull(type, "type requires not null");
        this.authority = authority;
        this.type = type;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
