package cn.com.mockingbird.robin.uaa.util;

import cn.com.mockingbird.robin.uaa.userdetails.SecurityUser;
import cn.com.mockingbird.robin.upm.api.common.SecurityConstants;
import cn.hutool.core.collection.ListUtil;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Security 工具类
 *
 * @author zhaopeng
 * @date 2023/12/2 0:13
 **/
@UtilityClass
public class SecurityUtils {

    /**
     * 返回 Authentication 实例
     * @return Authentication 实例
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 返回 SecurityUser 实例
     * @param authentication Authentication 实例
     * @return SecurityUser 实例
     */
    public SecurityUser getSecurityUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            return securityUser;
        }
        return null;
    }

    /**
     * 返回 SecurityUser 实例
     * @return SecurityUser 实例
     */
    public SecurityUser getSecurityUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getSecurityUser(authentication);
    }

    /**
     * 返回用户角色信息
     * @return 角色 IDs
     */
    public List<Long> getRoles() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return Collections.emptyList();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Long> roleIds = new ArrayList<>();
        authorities.stream()
                .filter(authority -> StringUtils.startsWith(authority.getAuthority(), SecurityConstants.ROLE))
                .forEach(authority -> {
                    String roleId = StringUtils.removeStart(authority.getAuthority(), SecurityConstants.ROLE);
                    roleIds.add(Long.parseLong(roleId));
                });
        return roleIds;
    }

}
