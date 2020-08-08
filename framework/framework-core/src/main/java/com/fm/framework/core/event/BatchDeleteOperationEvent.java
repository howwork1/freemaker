package com.fm.framework.core.event;

import com.fm.framework.core.model.BaseModel;

import java.util.Collection;

/**
 * <p>批量删除事件</p>
 *
 * @author clufeng
 */
public class BatchDeleteOperationEvent extends BatchOperationEvent {

    public BatchDeleteOperationEvent(Object source, Collection<? extends BaseModel> befores) {
        super(source, befores, null, OperationType.batch_delete);
    }
}
