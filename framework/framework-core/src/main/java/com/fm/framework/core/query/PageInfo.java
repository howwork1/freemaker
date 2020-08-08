package com.fm.framework.core.query;

import lombok.Data;

import java.util.List;

/**
 * <p>分页信息</p>
 *
 * @author clufeng
 */
@Data

public class PageInfo<E> implements Page<E> {

    /**
     * 当前页,默认第一页
     * */
    private int currentPage = 1;

    /**
     * 分页每页数据
     * */
    private int pageSize = 10; //默认一页10条数据

    /**
     * 分页查询后的数据
     */
    private List<E> data;

    /**
     * 数据总数量
     */
    private int total;

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getTotalPages() {
        return getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getPageSize());
    }
}
