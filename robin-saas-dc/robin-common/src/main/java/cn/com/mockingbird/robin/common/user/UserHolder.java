package cn.com.mockingbird.robin.common.user;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 用户信息持有者
 *
 * @author zhaopeng
 * @date 2023/10/5 21:43
 **/
public class UserHolder {

    private static final ThreadLocal<LoginUser> USER_CONTEXT = new TransmittableThreadLocal<>();

    /**
     * 存储用户信息
     * @param loginUser 登录用户
     */
    public static void save(LoginUser loginUser) {
        USER_CONTEXT.set(loginUser);
    }

    /**
     * 获取用户信息
     * @return LoginUser 实例
     */
    public static LoginUser getCurrentUser() {
        return USER_CONTEXT.get();
    }

    /**
     * 清空用户信息
     */
    public static void remove() {
        USER_CONTEXT.remove();
    }

}
