package com.fm.framework.core.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>Base Mode</p>
 *
 * @author clufeng
 */
@Data
public abstract class BaseModel {

    /**
     * 获取主键方法，主键整体平台定义成Long数据类型，方便数据的整体插入性能
     * @return 主键
     */
    public abstract Long getId();

    /**
     * 设置模型主键
     * @param id 主键
     */
    public abstract void setId(Long id);
    /**
     * 是否删除标志
     */
    private Boolean isDelete;

    /**
     * 时间戳
     */
    private LocalDateTime ts;


}
