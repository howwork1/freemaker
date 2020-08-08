package com.fm.framework.core.query;

import lombok.Getter;

import java.util.Objects;

/**
 * <p>排序字段</p>
 *
 * @author clufeng
 */
@Getter
public class OrderItem {

    private final String[] fields;

    private final OrderType type;

    public OrderItem(OrderType type, String... fields) {
        if(!Objects.isNull(type)) {
            this.type = type;
        } else {
            this.type = OrderType.asc;
        }
        this.fields = fields;
    }

}
