package com.fm.framework.core.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.fm.framework.core.event.UpdateStatusOperationEvent;
import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.model.DBFieldConst;
import com.fm.framework.core.model.DataStatus;
import com.fm.framework.core.model.IStatus;
import com.fm.framework.core.query.OrderItem;
import com.fm.framework.core.query.Page;
import com.fm.framework.core.query.QueryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>类描述</p>
 *
 * @author clufeng
 */
@Slf4j
public class StatusBaseService<M extends BaseMapper<T>, T extends BaseModel & IStatus>
        extends BaseService<M, T> implements IStatusService<T> {

    @Override
    protected void initDefaultField(T model) {
        super.initDefaultField(model);
        model.setStatus(DataStatus.enable.code());
    }

    /**
     * 是否带status= true的条件
     *
     * @param enable true带上条件
     * @return 查询wrapper
     */
    public QueryWrapper<T> getQueryWrapper(boolean enable) {
        QueryWrapper<T> queryWrapper = Wrappers.query();
        if (enable) {
            queryWrapper.eq(DBFieldConst.STATUS, DataStatus.enable.code());
        }
        return queryWrapper;
    }

    public List<T> getAllEnableStatus() {
        return list(getQueryWrapper(true));
    }

    @Override
    public List<T> getEnableStatus(List<QueryItem> queryItems) {
        return getEnableStatus(queryItems, null);
    }

    @Override
    public List<T> getEnableStatus(List<QueryItem> queryItems, OrderItem orderItem) {

        QueryWrapper<T> queryWrapper = getQueryWrapper(true);

        setQueryItem2Wrapper(queryItems, queryWrapper);

        setOrderItem2Wrapper(orderItem, queryWrapper);

        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public T getOneEnableStatus(List<QueryItem> queryItems) {

        QueryWrapper<T> queryWrapper = getQueryWrapper(true);

        setQueryItem2Wrapper(queryItems, queryWrapper);

        return this.getOne(queryWrapper);
    }

    @Override
    public int countEnableStatus(List<QueryItem> queryItems) {

        QueryWrapper<T> queryWrapper = getQueryWrapper(true);

        setQueryItem2Wrapper(queryItems, queryWrapper);

        return count(queryWrapper);
    }

    @Override
    public Page<T> listEnableStatus(long currPage, long pageSize) {
        if (currPage <= 0) {
            currPage = 1;
        }

        if (pageSize <= 0) {
            pageSize = 5;
        }

        return toPage(getBaseMapper().selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currPage, pageSize), getQueryWrapper(true)));
    }

    @Override
    public Page<T> listEnableStatus(List<QueryItem> queryItemList, OrderItem orderItem, long currPage, long pageSize) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(true);

        setQueryItem2Wrapper(queryItemList, queryWrapper);

        setOrderItem2Wrapper(orderItem, queryWrapper);

        return toPage(getBaseMapper().selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currPage, pageSize), queryWrapper));
    }

    @Override
    public Page<T> listEnableStatus(List<QueryItem> queryItemList, long currPage, long pageSize) {
        return listEnableStatus(queryItemList, null, currPage, pageSize);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setStatus(T t, boolean enable) {
        try {
            T newT = (T) t.getClass().newInstance();
            newT.setId(t.getId());
            newT.setTs(t.getTs());
            DataStatus value = enable ? DataStatus.enable : DataStatus.disable;
            newT.setStatus(value.code());
            boolean result = update(newT);
            if(result) {
                getApplicationContext().publishEvent(new UpdateStatusOperationEvent(this, Collections.singletonList(t), enable));
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setStatus(List<T> list, boolean enable) {

        if(CollectionUtils.isEmpty(list)) {
            return false;
        }

        list = list.stream().filter(t -> !t.getStatus().equals(enable ? DataStatus.enable.code() : DataStatus.disable.code())).collect(Collectors.toList());

        List<T> newlist = list.stream().map(t -> {
            try {
                T newT = (T) t.getClass().newInstance();
                newT.setId(t.getId());
                newT.setStatus(enable ? DataStatus.enable.code() : DataStatus.disable.code());
                return newT;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        boolean result = this.updateBatchById(newlist);

        if(result) {
            getApplicationContext().publishEvent(new UpdateStatusOperationEvent(this, list, enable));
        }

        return result;
    }

}
