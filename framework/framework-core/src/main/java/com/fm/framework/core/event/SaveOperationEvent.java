package com.fm.framework.core.event;


import com.fm.framework.core.model.BaseModel;

/**
 * 保存操作事件
 * @author clufeng
 * @version 1.0.0
 **/
public class SaveOperationEvent extends OperationEvent {

    public SaveOperationEvent(Object source, BaseModel after) {
        super(source, null, after, OperationType.save);
    }
}
