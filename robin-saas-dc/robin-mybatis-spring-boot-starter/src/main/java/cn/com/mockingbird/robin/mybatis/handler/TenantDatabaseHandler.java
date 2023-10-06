package cn.com.mockingbird.robin.mybatis.handler;

import cn.com.mockingbird.robin.common.user.UserHolder;
import cn.com.mockingbird.robin.mybatis.config.MultiTenantProperties;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO 租户处理器
 *
 * @author zhaopeng
 * @date 2023/10/6 22:40
 **/
public class TenantDatabaseHandler implements TenantLineHandler {

    private final Set<String> ignoredTables = new HashSet<>();

    public TenantDatabaseHandler(MultiTenantProperties multiTenantProperties) {
        // 大小写数据表名都添加，以防数据表定义的习惯不统一
        multiTenantProperties.getIgnoredTables().forEach(item -> {
            ignoredTables.add(item.toLowerCase());
            ignoredTables.add(item.toUpperCase());
        });
    }

    @Override
    public Expression getTenantId() {
        // 从用户上下文中获取租户ID
        return new LongValue(UserHolder.getCurrentUser().getTenantId());
    }

    @Override
    public String getTenantIdColumn() {
        // 暂时不需要重写，默认就是 tenant_id
        return TenantLineHandler.super.getTenantIdColumn();
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return ignoredTables.contains(tableName);
    }
}
