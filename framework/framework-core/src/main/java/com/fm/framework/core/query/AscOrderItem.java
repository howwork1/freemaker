package com.fm.framework.core.query;

/**
 * <p>升序排序</p>
 *
 * @author clufeng
 */
public class AscOrderItem extends OrderItem {
    public AscOrderItem(String... fields) {
        super(OrderType.asc, fields);
    }
}
