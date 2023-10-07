package cn.com.mockingbird.robin.mybatis.injector;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 增强的 SQL 注入器
 * <p>
 * 注入了 MyBatis-Plus 实现的 SQL 级的批量 Insert 数据的 AbstractMethod 子类 InsertBatchSomeColumn
 * @see InsertBatchSomeColumn
 * @author zhaopeng
 * @date 2023/10/8 0:59
 **/
public class EnhancedSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 更新时自动填充的字段不用插入值、ID自增的主键不用插入值
        methodList.add(new InsertBatchSomeColumn(column -> {
            TableId tableId = column.getField().getAnnotation(TableId.class);
            return column.getFieldFill() != FieldFill.UPDATE || (tableId != null && tableId.type() == IdType.AUTO);
        }));
        return methodList;
    }
}
