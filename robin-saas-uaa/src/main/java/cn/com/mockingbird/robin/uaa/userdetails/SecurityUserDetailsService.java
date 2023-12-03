package cn.com.mockingbird.robin.uaa.userdetails;

import cn.com.mockingbird.robin.upm.api.common.SecurityConstants;
import cn.com.mockingbird.robin.upm.api.dto.UserInfo;
import cn.com.mockingbird.robin.upm.api.entity.User;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 提供返回用户信息的接口
 *
 * @author zhaopeng
 * @date 2023/11/25 22:44
 **/
public interface SecurityUserDetailsService extends UserDetailsService, Ordered {

    /**
     * 是否支持指定客户端的认证
     * @param clientId 客户端 ID
     * @param grantType 鉴权类型
     * @return true - 支持
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值，默认取最大的
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }

    /**
     * 返回用户实体
     * @param securityUser 查询实例
     * @return 用户实例
     */
    default UserDetails loadUserByUsername(SecurityUser securityUser) {
        return this.loadUserByUsername(securityUser.getUsername());
    }

    /**
     * 返回 UserDetails 实例
     * @param userInfo 用户信息
     * @return UserDetails 实例
     */
    default UserDetails getUserDetails(UserInfo userInfo) {
        Set<String> set = new HashSet<>();
        if (ArrayUtils.isNotEmpty(userInfo.getRoles())) {
            Arrays.stream(userInfo.getRoles()).forEach(roleId -> {
                set.add(SecurityConstants.ROLE + roleId);
            });
            set.addAll(Arrays.asList(userInfo.getPermissions()));
        }
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(set.toArray(new String[0]));
        User user = userInfo.getUser();
        return new SecurityUser(user.getId(), user.getPhone(), user.getEmail(),
                user.getDepartmentId(), user.getPostId(), user.getUsername(),
                user.getPassword(), true, true, true,
                true, authorityList);
    }

}
