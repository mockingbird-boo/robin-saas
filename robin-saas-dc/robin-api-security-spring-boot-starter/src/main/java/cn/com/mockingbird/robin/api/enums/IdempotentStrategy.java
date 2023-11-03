package cn.com.mockingbird.robin.api.enums;

/**
 * 幂等防重策略
 *
 * @author zhaopeng
 * @date 2023/11/3 22:06
 **/
public enum IdempotentStrategy {

    /**
     * 客户端唯一标识防重
     */
    NONCE,
    /**
     * 服务器端基于请求信息（IP + 用户 + API）加锁防重
     */
    LOCK,
    /**
     * 基于服务器端提前生成的 TOKEN 防重
     */
    TOKEN


}
