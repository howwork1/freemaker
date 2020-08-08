package com.fm.framework.core.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fm.framework.core.Context;
import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.model.IAudit;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <p>审计服务</p>
 *
 * @author clufeng
 */
public class AuditBaseService<M extends BaseMapper<T>, T extends BaseModel & IAudit> extends BaseService<M, T> {

    @Override
    protected void initDefaultField(T model) {
        super.initDefaultField(model);
        model.setCreateTime(LocalDateTime.now());
        model.setCreateUser(Context.getCurrUser());
        model.setCreateUserCode(Context.getCurrUserCode());
    }

    @Override
    protected void beforeUpdate(T model) {
        super.beforeUpdate(model);
        model.setUpdateTime(LocalDateTime.now());
        model.setUpdateUser(Context.getCurrUser());
        model.setUpdateUserCode(Context.getCurrUserCode());
    }

    @Override
    protected void beforeUpdate(Collection<T> models) {
        super.beforeUpdate(models);
        if(!CollectionUtils.isEmpty(models)) {
            models.forEach(model -> {
                model.setUpdateTime(LocalDateTime.now());
                model.setUpdateUser(Context.getCurrUser());
            });
        }
    }
}
