package io.github.xxxspring.base.mysql.handler;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonMapTypeHandler extends BaseTypeHandler<Map> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map parameter, JdbcType jdbcType) throws SQLException {
        String value = JSON.toJSONString(parameter);
        ps.setString(i, value);
    }

    @Override
    public Map getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        Map map = JSON.parseObject(value, Map.class);
        return map;
    }

    @Override
    public Map getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        Map map = JSON.parseObject(value, Map.class);
        return map;
    }

    @Override
    public Map getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        Map map = JSON.parseObject(value, Map.class);
        return map;
    }
}
