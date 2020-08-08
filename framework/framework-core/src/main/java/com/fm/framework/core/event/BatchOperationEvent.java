package com.fm.framework.core.event;

import com.fm.framework.core.model.BaseModel;
import lombok.ToString;

import java.util.Collection;

/**
 * 批量操作事件
 * @author clufeng
 * @version 1.0.0
 **/
@ToString
public class BatchOperationEvent extends OperationEvent {

    private final Collection<? extends BaseModel> befores;

    private final Collection<? extends BaseModel> afters;

    public BatchOperationEvent(Object source, Collection<? extends BaseModel> befores, Collection<? extends BaseModel> afters, OperationType type) {
        super(source, null, null, type);
        this.befores = befores;
        this.afters = afters;
    }

    @Override
    public BaseModel getBefore() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseModel getAfter() {
        throw new UnsupportedOperationException();
    }

    public Collection<? extends BaseModel> getBefores() {
        return befores;
    }

    public Collection<? extends BaseModel> getAfters() {
        return afters;
    }
}
