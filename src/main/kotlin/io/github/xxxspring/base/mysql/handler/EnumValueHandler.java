package io.github.xxxspring.base.mysql.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@MappedJdbcTypes(JdbcType.NUMERIC)
public class EnumValueHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private Class<E> type;
    private E[] enums;
    private Map<Integer, E> enumMap;

    public EnumValueHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }

        try {
            enumMap = new HashMap<>();
            final Method method;
            method = type.getDeclaredMethod("getValue");


            Arrays.stream(enums).forEach(it -> {
                        try {
                            Integer value = (Integer) method.invoke(it);
                            if (enumMap.get(value) != null) {
                                throw new RepeatValueException(type.getSimpleName() + " can't have repeat value");
                            }
                            enumMap.put(value, it);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        try {
            Method method = type.getDeclaredMethod("getValue");
            ps.setInt(i, (Integer) method.invoke(parameter));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);

        if (i == 0 && rs.wasNull()) {
            return null;
        } else {
            try {
                return enumMap.get(i);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by value.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (i == 0 && rs.wasNull()) {
            return null;
        } else {
            try {
                return enumMap.get(i);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by value.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (i == 0 && cs.wasNull()) {
            return null;
        } else {
            try {
                return enumMap.get(i);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by value.", ex);
            }
        }
    }
}
