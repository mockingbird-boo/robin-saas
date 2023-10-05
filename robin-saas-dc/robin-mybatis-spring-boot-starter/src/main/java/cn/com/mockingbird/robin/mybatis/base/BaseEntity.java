package cn.com.mockingbird.robin.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
}
