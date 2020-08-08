package com.fm.framework.core.query;

import java.util.List;

/**
 * <p>分页接口</p>
 *
 * @author clufeng
 */
public interface Page<E> {
    /**
     * 获取每页数据条数
     * */
    int getPageSize();

    /**
     * 获取当前页
     * */
    int getCurrentPage();

    /**
     * 获取总共页数
     * */
    int getTotalPages();

    /**
     * 获取数据总计
     * */
    int getTotal();

    /**
     * 获取数据
     * */
    List<E> getData();
}
