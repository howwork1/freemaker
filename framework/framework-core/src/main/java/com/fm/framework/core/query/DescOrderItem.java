package com.fm.framework.core.query;

/**
 * <p>降序</p>
 *
 * @author clufeng
 */
public class DescOrderItem extends OrderItem {
    public DescOrderItem(String... fields) {
        super(OrderType.desc, fields);
    }
}
