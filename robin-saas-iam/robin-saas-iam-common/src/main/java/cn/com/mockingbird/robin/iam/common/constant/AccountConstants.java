package cn.com.mockingbird.robin.iam.common.constant;

import static cn.com.mockingbird.robin.iam.support.constant.IamConstants.API_V1_PREFIX;
import static cn.com.mockingbird.robin.iam.support.constant.IamConstants.ROOT_NODE;

/**
 * 账户常量
 *
 * @author zhaopeng
 * @date 2023/12/4 19:19
 **/
public interface AccountConstants {

    /**
     * 用户 API
     */
    String USER_API = API_V1_PREFIX + "/user";

    /**
     * 用户组 API
     */
    String USER_GROUP_API = API_V1_PREFIX + "/user-group";

    /**
     * 动态用户组 API
     */
    String DYNAMIC_USER_GROUP_API = API_V1_PREFIX + "/dynamic-user-group";

    /**
     * 组织 API
     */
    String ORGANIZATION_API = API_V1_PREFIX + "/organization";

    /**
     * 身份源 API
     */
    String IDENTITY_SOURCE_API = API_V1_PREFIX + "/identity-source";

    /**
     * 文档组名
     */
    String ACCOUNT_API_DOC_GROUP_NAME = "系统账户";

    /**
     * ACCOUNT_APIS
     */
    String[] ACCOUNT_APIS = {
            USER_API + "/**",
            ORGANIZATION_API + "/**",
            USER_GROUP_API + "/**",
            DYNAMIC_USER_GROUP_API + "/**",
            IDENTITY_SOURCE_API + "/**"
    };

    /**
     * IDS 缓存名
     */
    String IDS_CACHE_NAME = "ids";

    /**
     * 用户缓存名
     */
    String USER_CACHE_NAME = "user";

    /**
     * 组织缓存名
     */
    String ORG_CACHE_NAME = "organization";

    /**
     * 根部门 ID
     */
    String ROOT_DEPARTMENT_ID = ROOT_NODE;

    /**
     * 根部门名称
     */
    public static final String ROOT_DEPT_NAME = ROOT_NODE;

}
