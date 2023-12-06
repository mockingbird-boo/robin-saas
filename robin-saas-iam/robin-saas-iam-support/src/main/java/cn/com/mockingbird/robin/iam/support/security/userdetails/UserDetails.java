package cn.com.mockingbird.robin.iam.support.security.userdetails;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;

/**
 * 用户详情
 *
 * @author zhaopeng
 * @date 2023/12/6 0:38
 **/
@Getter
public class UserDetails extends User {

    @Serial
    private static final long serialVersionUID = -7354400034773358710L;

    private final String id;
    private final String username;
    private final UserType userType;

    public UserDetails(String id, String username, UserType userType, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.username = username;
        this.userType = userType;
    }

    public UserDetails(String id, String username, String password, UserType userType, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, Objects.isNull(password) ? "" : password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.username = username;
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            if (!super.equals(o)) {
                return false;
            } else {
                UserDetails details = (UserDetails)o;
                return StringUtils.equals(this.username, details.getUsername());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.username);
    }

    @Override
    public String toString() {
        return this.username;
    }
}
