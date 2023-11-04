package cn.com.mockingbird.robin.api.model;

import lombok.Data;

/**
 * 加密传输数据的请求体模型
 * <p><br/>
 * 如果 API 接口的请求数据的传输是加密的，要求这时候请求体可以反序列化成当前类的实例
 * <p><br/>
 * 如果 API 接口的请求数据的传输是加密的，并且还需要验签或者防重，这时候验签或防重需要的参数优先从解密数据中的取，
 * 因此该模型中除了定义了 data 和 key，还额外定义了其他参数，例如 nonce、signature、timestamp、token等。
 *
 * @author zhaopeng
 * @date 2023/11/2 19:03
 **/
@Data
public class RequestBodyData {

    /**
     * 请求中的加密数据，解密之后能反序列化成接口参数实例
     */
    private String data;

    /**
     * 客户端使用 RSA 公钥加密后的 AES 密钥
     */
    private String key;

    /**
     * 请求唯一标识，由客户端生成，比如使用（用户信息 + 时间戳 + 随机数）等信息生成的摘要
     */
    private String nonce;

    /**
     * 数字签名
     */
    private String signature;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 服务器端生成的幂等 Token
     */
    private String token;

    private transient String decryptedData;

}
