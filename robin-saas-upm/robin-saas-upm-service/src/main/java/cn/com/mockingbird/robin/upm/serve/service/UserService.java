package cn.com.mockingbird.robin.upm.serve.service;

import cn.com.mockingbird.robin.mybatis.service.ServiceImplX;
import cn.com.mockingbird.robin.upm.api.dto.UserInfo;
import cn.com.mockingbird.robin.upm.api.entity.User;
import cn.com.mockingbird.robin.upm.serve.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * User Service
 *
 * @author zhaopeng
 * @date 2023/11/30 1:48
 **/
@Service
public class UserService extends ServiceImplX<UserMapper, User> {

    public UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        // TODO 查询角色列表

        // TODO 查询权限列表
        return userInfo;
    }
}
