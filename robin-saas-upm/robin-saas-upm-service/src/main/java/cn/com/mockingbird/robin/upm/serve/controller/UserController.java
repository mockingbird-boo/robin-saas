package cn.com.mockingbird.robin.upm.serve.controller;

import cn.com.mockingbird.robin.upm.api.dto.UserInfo;
import cn.com.mockingbird.robin.upm.api.entity.User;
import cn.com.mockingbird.robin.upm.serve.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 Controller
 *
 * @author zhaopeng
 * @date 2023/11/27 17:56
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 根据 用户名/邮箱/手机号 获取用户信息
     * @param username 用户名/邮箱/手机号
     * @return 用户信息
     */
    @GetMapping("/{username}")
    public UserInfo getUserInfo(@PathVariable("username") String username) {
        User user = userService.getOne(new QueryWrapper<User>().lambda()
                .eq(User::getUsername, username).or()
                .eq(User::getEmail, username).or()
                .eq(User::getPhone, username)
        );
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return userService.getUserInfo(user);
    }

}
