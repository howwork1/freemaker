package com.fm.framework.core.event;




import com.fm.framework.core.model.BaseModel;

import java.util.Collection;

/**
 * <p>批量更新事件</p>
 *
 * @author clufeng
 */
public class BatchUpdateOperationEvent extends BatchOperationEvent {
    public BatchUpdateOperationEvent(Object source, Collection<? extends BaseModel> befores, Collection<? extends BaseModel> afters) {
        super(source, befores, afters, OperationType.batch_update);
    }
}
