package cn.com.mockingbird.robin.api.annotation;

import cn.com.mockingbird.robin.api.enums.DigestAlgorithm;
import cn.com.mockingbird.robin.api.enums.EncryptAlgorithm;
import cn.com.mockingbird.robin.api.enums.IdempotentStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * API 安全注解
 * <p>
 * 该注解用于标识 API 接口是安全接口。
 * <p>
 * 使用该注解的 API 接口通过注解属性进行配置，可以支持以下：
 * <ul>
 * <li> 防窃取：传输数据加密；
 * <li> 防伪装和篡改：数字签名；
 * <li> 防重复调用：支持多种防重策略；
 * <li> 返回数据加密：响应拦截。
 * </ul>
 *
 * @author zhaopeng
 * @date 2023/10/28 1:09
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ApiSecurity {

    @SuppressWarnings("unused")
    @AliasFor("encrypted")
    boolean value() default true;

    /**
     * 请求数据传输是否是加密的
     */
    @AliasFor("value")
    boolean encrypted() default true;

    /**
     * 客户端加密算法，默认是 RSA_AES，即 RSA 公钥加密 AES 密钥，AES 密钥加密实际请求参数
     * @see EncryptAlgorithm
     */
    EncryptAlgorithm encryptAlgorithm() default EncryptAlgorithm.RSA_AES;

    /**
     * API 请求是否需要进行数字签名认证。
     *
     * <p>
     * 签名一般由客户端生成，客户端使用约定好的信息摘要算法先生成摘要，然后使用 RSA 私钥
     * 对摘要进行加密形成数字签名。
     * <p>
     * 单纯依靠摘要算法不能严格地验证数据完整性，在非安全信道中，数据和摘要都存在篡改风险，
     * 攻击者在篡改数据时也可以篡改摘要。
     * <p>
     * 摘要的生成一般需要加随机盐，加盐有利于避免类似彩虹表攻击。
     */
    boolean signature() default false;

    /**
     * 客户端摘要算法，默认是 MD5
     * @see DigestAlgorithm
     */
    DigestAlgorithm digestAlgorithm() default DigestAlgorithm.MD5;

    /**
     * 数字签名有效期。
     * <p>
     * 单位是秒，默认60s，签名应该尽量避免长期有效以降低破解风险
     */
    long signatureValidityTime() default 60L;

    /**
     * 是否需要对合法请求进行防重处理
     */
    boolean idempotent() default true;

    /**
     * 幂等防重策略，默认采用 NONCE 策略
     * @see IdempotentStrategy
     */
    IdempotentStrategy strategy() default IdempotentStrategy.NONCE;

    /**
     * 幂等防重的有效期（单位：秒），过了有效期之后的合法请求可以再次请求，对于基于 NONCE 和 LOCK 的幂等策略才有意义。
     * <p>
     * 另外需要注意：如果当前接口既需要验证数字签名又需要幂等防重，验签逻辑优先。
     */
    long idempotentValidityTime() default 5L;

    /**
     * 幂等防重的提示消息
     */
    @SuppressWarnings("unused")
    String idempotentMessage() default "幂等性冲突";

    /**
     * 响应数据是否需要加密
     */
    boolean encryptResponse() default false;

}
