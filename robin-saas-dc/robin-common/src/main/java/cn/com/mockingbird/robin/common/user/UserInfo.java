package cn.com.mockingbird.robin.common.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统用户信息
 *
 * @author zhaopeng
 * @date 2023/11/25 23:36
 **/
@Data
public class UserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 5726062999966158089L;

    /**
     * 用户基本信息
     */
    private LoginUser loginUser;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色
     */
    private Long[] roles;

}
