package cn.com.mockingbird.robin.mybatis.config;

import cn.com.mockingbird.robin.mybatis.security.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.AES;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * 增强的 mybatis 配置
 *
 * @author zhaopeng
 * @date 2023/10/10 23:01
 **/
@Data
@ConfigurationProperties(prefix = "spring.mybatis-enhance")
public class EnhancedMybatisProperties {

    public static final String AES_KEY = AES.generateRandomKey();

    /**
     * 是否启用多租户插件
     */
    private Boolean enableMultiTenant = Boolean.FALSE;

    /**
     * 是否启用乐观锁插件
     */
    private Boolean enableOptimisticLock = Boolean.TRUE;

    /**
     * 是否启用数据加密
     */
    private Boolean enableDataEncrypt = Boolean.TRUE;

    /**
     * 是否启用非法SQL拦截插件
     * <p>
     * 支持 必须使用索引、全表更新操作检查、not or 子查询检查
     */
    private Boolean enableIllegalSqlIntercept = Boolean.FALSE;

    /**
     * 启用多租户插件需要忽略的数据表
     */
    private Set<String> ignoredTables = Collections.emptySet();

    /**
     * 忽略的请求，例如登录接口这时候还不知道租户信息
     * 在后续的开发中也许会使用到，暂且保留
     */
    private Set<String> ignoredUrls = Collections.emptySet();

    @Getter
    private final DataEncrypt dataEncrypt = new DataEncrypt();

    @Data
    public static class DataEncrypt {
        /**
         * 加密算法
         */
        private Algorithm algorithm = Algorithm.BASE64;

        /**
         * AES 加密密钥
         */
        private String aesKey = AES_KEY;

        /**
         * AES 偏移参数
         */
        private String aesIv = "0f7687fe684d7ed0";

    }

}
