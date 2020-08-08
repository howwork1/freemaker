package com.fm.framework.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fm.framework.core.event.*;
import com.fm.framework.core.id.IdGenerator;
import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.model.DBFieldConst;
import com.fm.framework.core.query.OrderItem;
import com.fm.framework.core.query.Page;
import com.fm.framework.core.query.PageInfo;
import com.fm.framework.core.query.QueryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通用BaseService
 *
 * @author clufeng
 * @version 1.0.0
 **/
@Slf4j
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T>
        implements IService<T>, Service<T>, ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Autowired(required = false)
    private IdGenerator idGenerator;

    @Value("${batchSize:200}")
    private int batchSize;


    @Override
    public boolean save(Collection<T> entityList) {

        entityList.forEach(this::initDefaultField);

        beforeSave(entityList);

        boolean result = super.saveBatch(entityList, batchSize);

        if (result) {
            afterSave(entityList);
            applicationContext.publishEvent(new BatchSaveOperationEvent(this, entityList));
        }

        return result;
    }

    protected Long genId(T model) {
        if(Objects.nonNull(idGenerator)) {
            return idGenerator.gen(SqlHelper.table(model.getClass()).getTableName());
        }
        return null;
    }

    protected void initDefaultField(T model) {
        model.setId(genId(model));
        model.setIsDelete(false);
        setNewTs(model);
    }

    @Override
    public boolean save(T model) {
        boolean result;
        initDefaultField(model);
        beforeSave(model);
        result = super.save(model);
        if (result) {
            afterSave(model);
            applicationContext.publishEvent(new SaveOperationEvent(this, model));
        }

        return result;
    }

    @Override
    public boolean saveOrUpdate(T model) {
        if (model.getId() == null) {
            return save(model);
        } else {
            return update(model);
        }
    }

    protected void setNewTs(T model) {
        //
        model.setTs(LocalDateTime.now());
    }

    public boolean delete(T model) {
        //validateTs(model);
        if(Objects.isNull(model)) {
            return false;
        }
        boolean result;
        beforeDelete(model);
        result = super.removeById(model.getId());
        if (result) {
            afterDelete(model);
            applicationContext.publishEvent(new DeleteOperationEvent(this, model));
        }
        return result;
    }

    public boolean delete(List<T> model) {

        if(model.isEmpty()) {
            return false;
        }

        boolean result;

        List<Long> ids = model.stream().map(BaseModel::getId).collect(Collectors.toList());

        beforeDelete(model);

        result = super.removeByIds(ids);

        if (result) {
            afterDelete(model);
            applicationContext.publishEvent(new BatchDeleteOperationEvent(this, model));
        }

        return result;

    }

    public boolean update(T model) {
        boolean result;
        beforeUpdate(model);
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(DBFieldConst.ID, model.getId()).eq(DBFieldConst.TS, model.getTs());
        T before = get(model.getId());
        result = update(model, updateWrapper);
        if (result) {
            afterUpdate(model);
            applicationContext.publishEvent(new UpdateOperationEvent(this, before, model));
        }
        return result;
    }

    @Override
    public boolean update(List<T> models) {

        boolean result;

        models.forEach(model -> {

        });

        beforeUpdate(models);

        List<T> befores = getByIds(models.stream().map(T::getId).collect(Collectors.toList()));

        result = this.updateBatchById(models, batchSize);

        if (result) {
            afterUpdate(models);
            applicationContext.publishEvent(new BatchUpdateOperationEvent(this, befores, models));
        }

        return false;
    }

    public T get(Long id) {
        return getById(id);
    }


    public List<T> getAll() {
        return list();
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public QueryWrapper<T> getQueryWrapper() {
        return Wrappers.query();
    }

    @Override
    public Page<T> list(long currPage, long pageSize) {

        if (currPage <= 0) {
            currPage = 1;
        }

        if (pageSize <= 0) {
            pageSize = 5;
        }

        return toPage(getBaseMapper().selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currPage, pageSize), getQueryWrapper()));
    }

    @Override
    public Page<T> list(List<QueryItem> queryItemList, OrderItem orderItem, long currPage, long pageSize) {

        QueryWrapper<T> queryWrapper = getQueryWrapper();

        setQueryItem2Wrapper(queryItemList, queryWrapper);

        setOrderItem2Wrapper(orderItem, queryWrapper);

        return toPage(getBaseMapper().selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currPage, pageSize), queryWrapper));

    }

    @Override
    public Page<T> list(OrderItem orderItem, long currPage, long pageSize) {

        QueryWrapper<T> queryWrapper = getQueryWrapper();

        setOrderItem2Wrapper(orderItem, queryWrapper);

        return toPage(getBaseMapper().selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currPage, pageSize), queryWrapper));

    }

    protected void setOrderItem2Wrapper(OrderItem orderItem, QueryWrapper<T> queryWrapper) {
        if (!Objects.isNull(orderItem)
                && !Objects.isNull(orderItem.getFields())
                && orderItem.getFields().length > 0) {
            switch (orderItem.getType()) {
                case asc:
                    queryWrapper.orderByAsc(orderItem.getFields());
                    break;
                case desc:
                    queryWrapper.orderByDesc(orderItem.getFields());
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void setQueryItem2Wrapper(List<QueryItem> queryItemList, QueryWrapper<T> queryWrapper) {
        if (queryItemList != null) {
            queryItemList.forEach(queryItem -> {
                switch (queryItem.getType()) {
                    case eq:
                        queryWrapper.eq(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case gt:
                        queryWrapper.gt(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case gte:
                        queryWrapper.ge(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case lt:
                        queryWrapper.lt(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case lte:
                        queryWrapper.le(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case ne:
                        queryWrapper.ne(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case like:
                        queryWrapper.like(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case leftLike:
                        queryWrapper.likeLeft(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case rightLike:
                        queryWrapper.likeRight(queryItem.getQueryField(), queryItem.getValue());
                        break;
                    case in:
                        Object value = queryItem.getValue();
                        if (value instanceof Collection) {
                            Collection<T> valueC = (Collection<T>) value;
                            queryWrapper.in(queryItem.getQueryField(), valueC);
                        } else {
                            queryWrapper.in(queryItem.getQueryField(), queryItem.getValue());
                        }
                        break;
                    case notIn:
                        Object val = queryItem.getValue();
                        if (val instanceof Collection) {
                            Collection<T> valueC = (Collection<T>) val;
                            queryWrapper.notIn(queryItem.getQueryField(), valueC);
                        } else {
                            queryWrapper.notIn(queryItem.getQueryField(), queryItem.getValue());
                        }
                    default:
                        break;
                }
            });
        }
    }

    @Override
    public Page<T> list(List<QueryItem> queryItemList, long currPage, long pageSize) {
        return list(queryItemList, null, currPage, pageSize);
    }

    @Override
    public List<T> get(List<QueryItem> queryItems) {

        return get(queryItems, null);
    }

    @Override
    public List<T> get(List<QueryItem> queryItems, OrderItem orderItem) {

        QueryWrapper<T> queryWrapper = getQueryWrapper();

        setQueryItem2Wrapper(queryItems, queryWrapper);

        setOrderItem2Wrapper(orderItem, queryWrapper);

        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public T getOne(List<QueryItem> queryItems) {

        QueryWrapper<T> queryWrapper = getQueryWrapper();

        setQueryItem2Wrapper(queryItems, queryWrapper);

        return this.getOne(queryWrapper);
    }

    @Override
    public int count(List<QueryItem> queryItems) {

        if(CollectionUtils.isEmpty(queryItems)) {
            return count();
        }

        QueryWrapper<T> queryWrapper = getQueryWrapper();

        setQueryItem2Wrapper(queryItems, queryWrapper);

        return count(queryWrapper);
    }

    @Override
    public List<T> getByIds(List<Long> ids) {

        if(CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return this.getBaseMapper().selectBatchIds(ids);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    protected void beforeSave(Collection<T> models) {
        //空方法等待子类去继承
    }

    protected void beforeSave(T model) {
        //空方法等待子类去继承
    }

    protected void afterSave(Collection<T> models) {
        //空方法等待子类去继承
    }

    protected void afterSave(T model) {
        //空方法等待子类去继承
    }
    protected void beforeDelete(T model) {
        //空方法等待子类去继承
    }

    protected void afterDelete(T model) {
        //空方法等待子类去继承
    }

    protected void beforeDelete(Collection<T> model) {
        //空方法等待子类去继承
    }

    protected void afterDelete(Collection<T> model) {
        //空方法等待子类去继承
    }

    protected void beforeUpdate(T model) {
        //空方法等待子类去继承
    }

    protected void afterUpdate(T model) {
        //空方法等待子类去继承
    }

    protected void beforeUpdate(Collection<T> models) {
        //空方法等待子类去继承
    }

    protected void afterUpdate(Collection<T> models) {
        //空方法等待子类去继承
    }

    protected Page<T> toPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> mybatisPlusPage){
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setCurrentPage((int)mybatisPlusPage.getCurrent());
        pageInfo.setPageSize((int) mybatisPlusPage.getSize());
        pageInfo.setTotal((int) mybatisPlusPage.getTotal());
        pageInfo.setData(mybatisPlusPage.getRecords());
        return pageInfo;
    }

}
