package com.fm.framework.core.service;

import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.query.OrderItem;
import com.fm.framework.core.query.Page;
import com.fm.framework.core.query.QueryItem;

import java.util.Collection;
import java.util.List;

/**
 * 基础服务接口，提供对单实体的增删改查
 * @author clufeng
 * @version 1.0.0
 **/
public interface Service<T extends BaseModel> {

    /**
     * 批量保存数据
     * @param entityList 数据集
     * @return 是否保存成功
     */
    boolean save(Collection<T> entityList);

    /**
     * 保存数据
     * @param model 数据信息
     * @return 是否保存成功
     */
    boolean save(T model);

    /**
     * 保存或者更新数据
     * @param model 数据
     * @return 是否成功
     */
    boolean saveOrUpdate(T model);

    /**
     * 批量删除数据
     * @param model 待删除的数据
     * @return 是否成功
     */
    boolean delete(List<T> model);

    /**
     * 更新数据
     * @param model 待更新的数据
     * @return 是否成功
     */
    boolean update(T model);

    /**
     * 更新数据
     * @param model 待更新的数据
     * @return 是否成功
     */
    boolean update(List<T> model);

    /**
     * 删除数据
     * @param model 待删除的数据
     * @return 是否成功
     */
    boolean delete(T model);

    /**
     * 删除数据
     * @param id 待删除的数据ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 根据ID过去数据
     * @param id id
     * @return id获取的数据
     */
    T get(Long id);

    /**
     * 根据ID过去数据
     * @param ids ids
     * @return id获取的数据
     */
    List<T> getByIds(List<Long> ids);

    /**
     * 获取所有数据，包括禁用状态的数据
     * @return 所有数据
     */
    List<T> getAll();

    /**
     * 根据查询条件获取数据
     * @param queryItems 查询条件
     * @return 数据
     */
    List<T> get(List<QueryItem> queryItems);


    /**
     * 根据查询条件和排序字段获取数据
     * @param queryItems 查询条件
     * @param orderItem 排序字段
     * @return 数据
     */
    List<T> get(List<QueryItem> queryItems, OrderItem orderItem);



    /**
     * 根据查询条件获取一条数据
     * @param queryItems 查询条件列表
     * @return 数据
     */
    T getOne(List<QueryItem> queryItems);

    /**
     * 根据查询条件获取获取数量
     * @param queryItems 条件列表
     * @return 数量信息
     */
    int count(List<QueryItem> queryItems);



    /**
     * 分页查询数据
     * @param currPage 当前页
     * @param pageSize 页面数据数量
     * @return 分页数据
     */
    Page<T> list(long currPage, long pageSize);



    /**
     * 分页查询数据，支持查询条件及排序字段
     * @param queryItemList 查询条件
     * @param currPage 当前页
     * @param pageSize 页面数量
     * @return 分页数据
     */
    Page<T> list(List<QueryItem> queryItemList, OrderItem orderItem, long currPage, long pageSize);

    /**
     * 分页查询数据，支持排序字段
     * @param orderItem 排序字段
     * @param currPage 当前页
     * @param pageSize 每页数量
     * @return 分页数据
     */
    Page<T> list(OrderItem orderItem, long currPage, long pageSize);


    /**
     * 分页查询数据，支持查询条件
     * @param queryItemList 查询条件
     * @param currPage 当前页
     * @param pageSize 页面数量
     * @return 分页数据
     */
    Page<T> list(List<QueryItem> queryItemList, long currPage, long pageSize);




}
