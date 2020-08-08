package com.fm.framework.core.event;

import com.fm.framework.core.model.BaseModel;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * 保存操作事件
 * @author clufeng
 * @version 1.0.0
 **/
@ToString
public class OperationEvent extends ApplicationEvent {

    private BaseModel before;

    private BaseModel after;

    private OperationType type;

    public OperationEvent(Object source, BaseModel before, BaseModel after, OperationType type) {
        super(source);
        this.before = before;
        this.after = after;
        this.type = type;
    }

    public BaseModel getBefore() {
        return before;
    }

    public BaseModel getAfter() {
        return after;
    }

    public OperationType getType() {
        return type;
    }

    public void clean() {
        before = null;
        after = null;
        source = null;
    }

}
