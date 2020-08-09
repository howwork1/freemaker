package com.fm.framework.web.request;


import lombok.Data;

/**
 * <p>分页信息</p>
 *
 * @author clufeng
 */
@Data
public class Pagination {

    //分页页码
    private Integer currentPage;

    //分页大小
    private Integer pageSize;

}
