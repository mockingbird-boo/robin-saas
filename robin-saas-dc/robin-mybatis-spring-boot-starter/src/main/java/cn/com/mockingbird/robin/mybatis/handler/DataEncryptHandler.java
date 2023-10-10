package cn.com.mockingbird.robin.mybatis.handler;

import cn.com.mockingbird.robin.mybatis.security.DataEncryptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据加密处理器
 * <p>
 * 本质上也是一种类型处理器 {@link TypeHandler}
 * 因为该处理器会使用到 Spring 容器中的 {@link DataEncryptor}，因此和其他类型处理器不同，它需要注入到 Spring 容器中
 *
 * @author zhaopeng
 * @date 2023/10/11 1:06
 **/
public class DataEncryptHandler<T> extends BaseTypeHandler<T> {

    @Autowired(required = false)
    private DataEncryptor dataEncryptor;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (dataEncryptor != null) {
            ps.setString(i, dataEncryptor.encrypt((String) parameter));
        } else {
            ps.setString(i, (String) parameter);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return StringUtils.isBlank(columnValue) || dataEncryptor == null ? (T) columnValue : (T) dataEncryptor.decrypt(columnValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return StringUtils.isBlank(columnValue) || dataEncryptor == null ? (T) columnValue : (T) dataEncryptor.decrypt(columnValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return StringUtils.isBlank(columnValue) || dataEncryptor == null ? (T) columnValue : (T) dataEncryptor.decrypt(columnValue);
    }
}
