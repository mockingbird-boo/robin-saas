package cn.com.mockingbird.robin.uaa.userdetails;

import cn.com.mockingbird.robin.cache.core.cache.MultiLevelCache;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 系统用户详细信息
 *
 * @author zhaopeng
 * @date 2023/11/26 2:22
 **/
public class SystemUserDetailsServiceImpl implements SystemUserDetailsService {

    @Resource
    private MultiLevelCache cache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 从缓存中获取信息
        // TODO 缓存中不存在就 UPM 中获取
        return null;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
