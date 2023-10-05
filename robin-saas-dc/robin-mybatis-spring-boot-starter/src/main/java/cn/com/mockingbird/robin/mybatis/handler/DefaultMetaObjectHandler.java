package cn.com.mockingbird.robin.mybatis.handler;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * mybatis-plus 公共字段处理器
 *
 * @author zhaopeng
 * @date 2023/10/5 20:46
 **/
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
            LocalDateTime now = LocalDateTime.now();
            if (baseEntity.getCreatedTime() == null) {
                baseEntity.setCreatedTime(now);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

}
