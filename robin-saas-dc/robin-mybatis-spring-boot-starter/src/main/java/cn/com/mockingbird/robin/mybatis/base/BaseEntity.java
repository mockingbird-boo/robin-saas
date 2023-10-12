package cn.com.mockingbird.robin.mybatis.base;

import cn.com.mockingbird.robin.mybatis.query.Condition;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * mybatis-plus 实体基类
 *
 * @author zhaopeng
 * @date 2023/10/5 20:50
 **/
@Data
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdUser;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedUser;
    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDeleted;
}
