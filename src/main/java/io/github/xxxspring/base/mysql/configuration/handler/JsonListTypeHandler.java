package io.github.xxxspring.base.mysql.configuration.handler;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonListTypeHandler extends BaseTypeHandler<List> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List parameter, JdbcType jdbcType) throws SQLException {
        String value = JSON.toJSONString(parameter);
        ps.setString(i, value);
    }

    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        List list = JSON.parseObject(value, List.class);
        return list;
    }

    @Override
    public List getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        List list = JSON.parseObject(value, List.class);
        return list;
    }

    @Override
    public List getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        List list = JSON.parseObject(value, List.class);
        return list;
    }
}
