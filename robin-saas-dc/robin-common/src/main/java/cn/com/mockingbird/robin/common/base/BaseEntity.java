package cn.com.mockingbird.robin.common.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 数据实体基类
 *
 * @author zhaopeng
 * @date 2023/10/3 0:26
 **/
@Getter
@Setter
public abstract class BaseEntity {

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 创建人
     */
    private String createdUser;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 更新人
     */
    private String updatedUser;

}
