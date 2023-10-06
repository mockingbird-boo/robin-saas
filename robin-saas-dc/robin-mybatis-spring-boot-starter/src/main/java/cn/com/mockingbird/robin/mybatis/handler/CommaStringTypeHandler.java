package cn.com.mockingbird.robin.mybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 英文逗号字符串类型转换器
 * <p>
 * 可以将数据库中英文逗号分隔的字符串类型转换成 List<String> 类型
 *
 * @author zhaopeng
 * @date 2023/10/6 10:24
 **/
public class CommaStringTypeHandler implements TypeHandler<List<String>> {

    private static final String COMMA = ",";

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
