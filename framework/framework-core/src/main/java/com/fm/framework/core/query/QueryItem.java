package com.fm.framework.core.query;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>查询项</p>
 *
 * @author clufeng
 */
@Data
@Accessors(chain = true)
public class QueryItem {

    private String queryField;

    private Object value;

    private QueryType type;

}
