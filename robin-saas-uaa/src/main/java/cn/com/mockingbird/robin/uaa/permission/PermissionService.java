package cn.com.mockingbird.robin.uaa.permission;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;

import java.util.Collection;

/**
 * 权限服务类
 *
 * @author zhaopeng
 * @date 2023/11/30 3:20
 **/
public class PermissionService {

    /**
     * 判断接口调用是否拥有权限
     * @param permissions 权限
     * @return true - 是；false - 否
     */
    public boolean hasPermission(String... permissions) {
        if (ArrayUtils.isEmpty(permissions)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority)
                .filter(StringUtils::isNotBlank)
                .anyMatch(item -> PatternMatchUtils.simpleMatch(permissions, item));
    }

}
