package com.fm.framework.core.utils;


import com.fm.framework.core.model.BaseModel;

/**
 * <p>类描述</p>
 *
 * @author clufeng
 */
public interface UpdateMatcher<T extends BaseModel> {

    boolean match(T old, T newT);

}
