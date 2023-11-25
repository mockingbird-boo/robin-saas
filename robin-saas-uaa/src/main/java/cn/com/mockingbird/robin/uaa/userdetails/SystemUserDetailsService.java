package cn.com.mockingbird.robin.uaa.userdetails;

import cn.com.mockingbird.robin.common.user.LoginUser;
import cn.com.mockingbird.robin.common.user.UserInfo;
import cn.com.mockingbird.robin.web.mvc.ResponseData;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

/**
 * 提供系统用户信息的接口
 *
 * @author zhaopeng
 * @date 2023/11/25 22:44
 **/
public interface SystemUserDetailsService extends UserDetailsService, Ordered {

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
     * @param systemUser 查询实例
     * @return 用户实例
     */
    default UserDetails loadSystemUser(SystemUser systemUser) {
        return this.loadUserByUsername(systemUser.getUsername());
    }

    /**
     * 返回 UserDetails
     * @param responseData 响应数据
     * @return UserDetails 实例
     */
    default UserDetails getUserDetails(ResponseData<UserInfo> responseData) {
        UserInfo userInfo = Optional.ofNullable(responseData.getData()).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        Set<String> set = new HashSet<>();

        if (ArrayUtils.isNotEmpty(userInfo.getRoles())) {
            // 获取角色
            Arrays.stream(userInfo.getRoles()).forEach(role -> {
                set.add("ROLE_" + role);
            });
            set.addAll(Arrays.asList(userInfo.getPermissions()));
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(set.toArray(new String[0]));
        LoginUser loginUser = userInfo.getLoginUser();
        return new SystemUser(loginUser.getId(), loginUser.getTenantId(), loginUser.getRoleId(),
                loginUser.getDepartmentId(), loginUser.getPhone(), loginUser.getUsername(),
                loginUser.getPassword(), true, true, true,
                false, authorityList);
    }

}
