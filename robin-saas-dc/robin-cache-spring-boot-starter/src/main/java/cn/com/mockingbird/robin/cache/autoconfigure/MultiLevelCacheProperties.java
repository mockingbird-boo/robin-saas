package cn.com.mockingbird.robin.cache.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存属性配置类
 *
 * @author zhaopeng
 * @date 2023/11/21 20:02
 **/
@Data
@ConfigurationProperties("spring.multi-level-cache")
public class MultiLevelCacheProperties {

    /**
     * 缓存名称
     */
    private String name = "multi-level-cache";

    /**
     * 一级缓存（本地缓存）名称
     */
    private String l1Name = "l1-cache";

    /**
     * 二级缓存（分布式缓存）名称
     */
    private String l2Name = "l2-cache";

    /**
     * 一级缓存最大容量内存占比
     */
    private Double maxCapacityMemoryRatio = 0.2;

    /**
     * 一级缓存初始化容量最大容量占比
     */
    private Double initCapacityRatio = 0.5;

    /**
     * 一级缓存默认过期时间，单位：秒
     */
    private int l1TimeToLive = 300;

    /**
     * 二级缓存默认过期时间，单位：秒
     */
    private int l2TimeToLive = 600;

    /**
     * 是否启用一级缓存
     */
    private Boolean enableL1Cache = true;

}
