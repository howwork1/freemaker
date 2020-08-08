package com.fm.framework.core.event;

import com.fm.framework.core.model.BaseModel;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * <p>更新状态事件</p>
 *
 * @author clufeng
 */
public class UpdateStatusOperationEvent extends ApplicationEvent {

    public UpdateStatusOperationEvent(Object source, List<? extends BaseModel> models, boolean enable) {
        super(source);
    }
}
