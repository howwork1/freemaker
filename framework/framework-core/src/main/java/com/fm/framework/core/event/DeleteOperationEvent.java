package com.fm.framework.core.event;


import com.fm.framework.core.model.BaseModel;

/**
 * 删除操作事件
 * @author clufeng
 * @version 1.0.0
 **/
public class DeleteOperationEvent extends OperationEvent {

    public DeleteOperationEvent(Object source, BaseModel before) {
        super(source, before, null, OperationType.delete);
    }
}
