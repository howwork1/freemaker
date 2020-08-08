package com.fm.framework.core.model;

/**
 * 数据状态
 * @author clufeng
 * @version 1.0.0
 **/
public enum DataStatus {
    /**
     * 启用
     */
    enable(1),
    /**
     * 禁用
     */
    disable(0);

    /**
     * 编码
     */
    private int code;

    DataStatus(int code) {
        this.code = code;
    }

    public int code(){
        return this.code;
    }
}
