package cn.com.mockingbird.robin.iam.common.constant;

import cn.com.mockingbird.robin.iam.support.constant.IamConstants;

/**
 * 授权相关常量
 *
 * @author zhaopeng
 * @date 2023/12/5 0:34
 **/
public interface AuthorizeConstants {

    /**
     * 登录 API
     */
    String LOGIN_API = IamConstants.API_V1_PREFIX + "/login";

    String AUTHORIZE_API = IamConstants.API_V1_PREFIX + "/authorize";

    String AUTHORIZATION_API = IamConstants.API_V1_PREFIX + "/authorization";

    /**
     * 表单登录路径
     */
    String FORM_LOGIN_API = LOGIN_API;

    /**
     * 验证码登录路径
     */
    String OTP_LOGIN_API = LOGIN_API + "/otp";

    /**
     * 登录配置 API
     */
    String LOGIN_CONFIG_API = LOGIN_API + "/config";

    /**
     * 前端登录路由地址
     */
    String FRONT_LOGIN_API = "/login";

    String LOGOUT = IamConstants.API_V1_PREFIX + "/logout";

}
