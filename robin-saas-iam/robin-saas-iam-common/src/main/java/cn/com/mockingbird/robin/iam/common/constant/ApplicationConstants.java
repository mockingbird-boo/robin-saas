package cn.com.mockingbird.robin.iam.common.constant;

import static cn.com.mockingbird.robin.iam.support.constant.IamConstants.API_V1_PREFIX;

/**
 * 应用管理常量
 *
 * @author zhaopeng
 * @date 2023/12/5 0:23
 **/
public interface ApplicationConstants {

    /**
     * 应用管理 API
     */
    String APPLICATION_API = API_V1_PREFIX + "/application";

    /**
     * 应用组管理 API
     */
    String APPLICATION_GROUP_API = API_V1_PREFIX + "/application-group";

    /**
     * 应用管理 API 文档组名称
     */
    String APPLICATION_API_DOC_GROUP_NAME = "应用管理";

    /**
     * 应用组管理 API 文档组名称
     */
    String APPLICATION_GROUP_API_DOC_GROUP_NAME = "应用组管理";

    /**
     * 应用缓存名
     */
    String APPLICATION_CACHE_NAME = "application";

    /**
     * 应用组缓存名
     */
    String APPLICATION_GROUP_CACHE_NAME = "application-group";

    /**
     * 应用基本信息缓存名
     */
    String APPLICATION_BASE_INFO_CACHE_NAME = "application:info";

    /**
     * 应用组基本信息缓存名
     */
    String APPLICATION_GROUP_BASE_INFO_CACHE_NAME = "application-group:info";
}
