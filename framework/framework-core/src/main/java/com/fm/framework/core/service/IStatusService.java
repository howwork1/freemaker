package com.fm.framework.core.service;

import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.model.IStatus;
import com.fm.framework.core.query.OrderItem;
import com.fm.framework.core.query.Page;
import com.fm.framework.core.query.QueryItem;

import java.util.List;

/**
 * <p>接口描述</p>
 *
 * @author hubo
 */
public interface IStatusService<T extends BaseModel & IStatus> extends Service<T> {

    /**
     * 获取所有状态为启用的数据
     * @return 所有数据
     */
    List<T> getAllEnableStatus();

    /**
     * 根据查询条件获取数据
     * @param queryItems 查询条件
     * @return 数据
     */
    List<T> getEnableStatus(List<QueryItem> queryItems);

    /**
     * 根据查询条件和排序字段获取数据
     * @param queryItems 查询条件
     * @param orderItem 排序字段
     * @return 数据
     */
    List<T> getEnableStatus(List<QueryItem> queryItems, OrderItem orderItem);

    /**
     * 根据查询条件获取一条数据
     * @param queryItems 查询条件列表
     * @return 数据
     */
    T getOneEnableStatus(List<QueryItem> queryItems);

    /**
     * 根据查询条件获取获取数量
     * @param queryItems 条件列表
     * @return 数量信息
     */
    int countEnableStatus(List<QueryItem> queryItems);

    /**
     * 分页查询数据
     * @param currPage 当前页
     * @param pageSize 页面数据数量
     * @return 分页数据
     */
    Page<T> listEnableStatus(long currPage, long pageSize);

    /**
     * 分页查询数据，支持查询条件及排序字段
     * @param queryItemList 查询条件
     * @param currPage 当前页
     * @param pageSize 页面数量
     * @return 分页数据
     */
    Page<T> listEnableStatus(List<QueryItem> queryItemList, OrderItem orderItem, long currPage, long pageSize);

    /**
     * 分页查询数据，支持查询条件
     * @param queryItemList 查询条件
     * @param currPage 当前页
     * @param pageSize 页面数量
     * @return 分页数据
     */
    Page<T> listEnableStatus(List<QueryItem> queryItemList, long currPage, long pageSize);

    /**
     * 设置状态信息
     * @param t 实体
     * @param enable 待设置状态
     * @return 是否成功
     */
    boolean setStatus(T t, boolean enable);

    /**
     * 批量设置状态信息
     * @param t 实体集合
     * @param enable 待设置状态
     * @return 是否成功
     */
    boolean setStatus(List<T> t, boolean enable);

}
