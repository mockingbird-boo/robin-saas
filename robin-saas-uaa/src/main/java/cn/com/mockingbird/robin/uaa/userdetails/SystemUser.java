package cn.com.mockingbird.robin.uaa.userdetails;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 *
 * @author zhaopeng
 * @date 2023/11/25 21:43
 **/
public class SystemUser extends User implements OAuth2AuthenticatedPrincipal {

    @Serial
    private static final long serialVersionUID = -2340523111684162640L;

    /**
     * 增强属性，用于存储 oauth2 上下文相关信息
     */
    private final Map<String, Object> enhancedAttributes = new HashMap<>();

    /**
     * 用户 ID
     */
    @Getter
    private final Long id;

    /**
     * 租户 ID
     */
    @Getter
    private final Long tenantId;

    /**
     * 角色 ID
     */
    @Getter
    private final Long roleId;

    /**
     * 部门 ID
     */
    @Getter
    private final Long departmentId;

    /**
     * 手机号
     */
    @Getter
    private final String phone;


    public SystemUser(Long id, Long tenantId, Long roleId, Long departmentId, String phone, String username, String password,
                      boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.tenantId = tenantId;
        this.roleId = roleId;
        this.departmentId = departmentId;
        this.phone = phone;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.enhancedAttributes;
    }

    @Override
    public String getName() {
        return super.getUsername();
    }
}
