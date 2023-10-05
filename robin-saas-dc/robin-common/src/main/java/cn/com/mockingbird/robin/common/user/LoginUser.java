package cn.com.mockingbird.robin.common.user;

import lombok.Data;

/**
 * 登录用户
 *
 * TODO 字段还需增加
 *
 * @author zhaopeng
 * @date 2023/10/5 21:35
 **/
@Data
public class LoginUser {

    private Long id;

    private Long tenantId;

    private Long departmentId;

    private String username;

    private String password;

    private String name;



}
