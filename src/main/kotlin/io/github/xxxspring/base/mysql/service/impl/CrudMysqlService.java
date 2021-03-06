package io.github.xxxspring.base.mysql.service.impl;

import io.github.xxxspring.base.entity.CursorList;
import io.github.xxxspring.base.entity.CursorQuery;
import io.github.xxxspring.base.entity.PageList;
import io.github.xxxspring.base.entity.PageQuery;
import io.github.xxxspring.base.service.CrudService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class CrudMysqlService<K, T> implements CrudService<K, T> {

    public abstract Mapper<T> getMapper();

    @Nullable
    @Override
    public T retrieve(K id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Nullable
    @Override
    public List<T> batchRetrieve(@NotNull List<? extends K> ids) {
        return null;
    }

    @Override
    public int create(T entity) {
        return getMapper().insertSelective(entity);
    }

    @Override
    public int update(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(K id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public void batchDelete(@NotNull List<? extends K> ids) {

    }

    @NotNull
    @Override
    public PageList<T> page(@NotNull PageQuery query) {
        return null;
    }

    @NotNull
    @Override
    public CursorList<T> cursor(@NotNull CursorQuery cursorQuery) {
        return null;
    }
}
