package com.fm.framework.core.utils;

import com.fm.framework.core.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * 字表对象在更新时比较的结果
 *
 * @author clufeng
 */
@Data
public class SubModelCompareResult<T extends BaseModel> {

    private List<T> newList;

    private List<T> updateList;

    private List<T> delList;

}
