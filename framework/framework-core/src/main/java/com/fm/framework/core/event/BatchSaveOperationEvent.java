package com.fm.framework.core.event;



import com.fm.framework.core.model.BaseModel;

import java.util.Collection;

/**
 * <p>批量保存事件</p>
 *
 * @author clufeng
 */
public class BatchSaveOperationEvent extends BatchOperationEvent {

    public BatchSaveOperationEvent(Object source, Collection<? extends BaseModel> afters) {
        super(source, null, afters, OperationType.batch_save);
    }

}
