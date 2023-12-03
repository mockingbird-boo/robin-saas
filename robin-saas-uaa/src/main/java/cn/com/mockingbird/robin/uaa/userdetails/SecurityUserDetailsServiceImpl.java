package cn.com.mockingbird.robin.uaa.userdetails;

import cn.com.mockingbird.robin.cache.core.cache.MultiLevelCache;
import cn.com.mockingbird.robin.common.util.response.ResponseCode;
import cn.com.mockingbird.robin.common.util.response.ResponseData;
import cn.com.mockingbird.robin.upm.api.common.SecurityConstants;
import cn.com.mockingbird.robin.upm.api.dto.UserInfo;
import cn.com.mockingbird.robin.upm.api.fegin.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 系统用户详细信息
 *
 * @author zhaopeng
 * @date 2023/11/26 2:22
 **/
@Slf4j
@Primary
@RequiredArgsConstructor
public class SecurityUserDetailsServiceImpl implements SecurityUserDetailsService {

    private final RemoteUserService remoteUserService;

    private final MultiLevelCache cache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = cache.get(SecurityConstants.USER_DETAILS_CACHE_PREFIX + username, SecurityUser.class);
        if (userDetails != null) {
            return userDetails;
        }
        ResponseData<UserInfo> userResponse = remoteUserService.getUser(username);
        if (ResponseData.isSuccess(userResponse) ) {

        }
        if (userResponse.getStatus() == ResponseCode.OK.getCode() && null != userResponse.getData()) {
            UserInfo userInfo = userResponse.getData();
            userDetails = getUserDetails(userInfo);
            cache.put(SecurityConstants.USER_DETAILS_CACHE_PREFIX + username, userDetails);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
        return userDetails;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
