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
 * 安全用户
 *
 * @author zhaopeng
 * @date 2023/11/25 21:43
 **/
public class SecurityUser extends User implements OAuth2AuthenticatedPrincipal {

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
     * 手机号
     */
    @Getter
    private final String phone;

    /**
     * 邮箱
     */
    @Getter
    private final String email;

    /**
     * 部门 ID
     */
    @Getter
    private final Long departmentId;

    /**
     * 岗位 ID
     */
    @Getter
    private final Long postId;

    public SecurityUser(Long id, String phone, String email, Long departmentId, Long postId, String username, String password,
                        boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                        boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.departmentId = departmentId;
        this.postId = postId;
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
