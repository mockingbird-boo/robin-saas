package cn.com.mockingbird.robin.mybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 英文逗号字符串 <--> List<String> 类型转换器
 * <p>
 * 支持数据库中英文逗号分隔的 varchar 类型 与 List<String> 类型之间转换
 *
 * @author zhaopeng
 * @date 2023/10/6 10:24
 **/
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
@SuppressWarnings("unused")
public class CommaStringTypeHandler implements TypeHandler<List<String>> {

    private static final String COMMA = ",";

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // 设置占位符
        ps.setString(i, String.join(COMMA, parameter));
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return getResult(columnValue);
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return getResult(columnValue);
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return getResult(columnValue);
    }

    /**
     * 逗号格式字符串转StringList
     * @param value 逗号格式字符串
     * @return String List
     */
    private List<String> getResult(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.asList(value.trim().split(COMMA));
    }
}
