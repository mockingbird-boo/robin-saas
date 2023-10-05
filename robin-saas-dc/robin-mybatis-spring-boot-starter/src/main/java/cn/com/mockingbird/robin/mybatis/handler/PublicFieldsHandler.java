package cn.com.mockingbird.robin.mybatis.handler;

import cn.com.mockingbird.robin.common.user.LoginUser;
import cn.com.mockingbird.robin.common.user.UserHolder;
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
public class PublicFieldsHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
            LocalDateTime now = LocalDateTime.now();
            if (baseEntity.getCreatedTime() == null) {
                baseEntity.setCreatedTime(now);
            }
            if (baseEntity.getUpdatedTime() == null) {
                baseEntity.setUpdatedTime(now);
            }
            LoginUser currentUser = UserHolder.getCurrentUser();
            if (currentUser != null && baseEntity.getCreatedUser() == null) {
                baseEntity.setCreatedUser(currentUser.getUsername());
            }
            if (currentUser != null && baseEntity.getUpdatedUser() == null) {
                baseEntity.setUpdatedUser(currentUser.getUsername());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updatedTime = getFieldValByName("updatedTime", metaObject);
        if (updatedTime == null) {
            setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
        }

        Object updatedUser = getFieldValByName("updatedUser", metaObject);
        LoginUser currentUser = UserHolder.getCurrentUser();
        if (updatedUser == null && currentUser != null) {
            setFieldValByName("updatedUser", currentUser.getUsername(), metaObject);
        }
    }

}
