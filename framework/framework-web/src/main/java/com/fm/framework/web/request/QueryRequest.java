package com.fm.framework.web.request;

import com.fm.framework.core.query.OrderItem;
import com.fm.framework.core.query.QueryItem;
import lombok.Data;

import java.util.List;

/**
 * <p>带分页的查询条件</p>
 *
 * @author clufeng
 */
@Data
public class QueryRequest {

    //查询条件
    private List<QueryItem> queryItems;

    //排序
    private OrderItem orderItem;

    //分页
    private Pagination pagination;
}
