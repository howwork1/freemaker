package com.fm.framework.core.event;


import com.fm.framework.core.model.BaseModel;

/**
 * 更新操作事件
 * @author clufeng
 * @version 1.0.0
 **/
public class UpdateOperationEvent extends OperationEvent {
    public UpdateOperationEvent(Object source, BaseModel before, BaseModel after) {
        super(source, before, after, OperationType.update);
    }
}
