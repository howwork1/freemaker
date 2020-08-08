package com.fm.framework.core.model;

import java.time.LocalDateTime;

/**
 * 审计接口
 * @author clufeng
 * @version 1.0.0
 **/
public interface IAudit {

    /**
     * 获取创建时间
     * @return 创建时间
     */
    LocalDateTime getCreateTime();

    /**
     * 设置创建时间
     * @param createTime 创建时间
     */
    void setCreateTime(LocalDateTime createTime);

    /**
     * 获取更新时间
     * @return 更新时间
     */
    LocalDateTime getUpdateTime();

    /**
     * 设置更新时间
     * @param updateTime 更新时间
     */
    void setUpdateTime(LocalDateTime updateTime);

    /**
     * 获取创建人
     * @return 创建人ID
     */
    Long getCreateUser();

    /**
     * 获取创建人编码
     * @return 编码
     */
    String getCreateUserCode();

    /**
     * 设置创建人
     * @param createUser 创建人ID
     */
    void setCreateUser(Long createUser);


    /**
     * 设置创建人编码
     * @param code 编码
     */
    void setCreateUserCode(String code);

    /**
     * 获取更新用户
     * @return 更新用户
     */
    Long getUpdateUser();

    /**
     * 获取创建人编码
     * @return 编码
     */
    String getUpdateUserCode();

    /**
     * 设置更新人
     * @param updateUser 更新人ID
     */
    void setUpdateUser(Long updateUser);

    /**
     * 设置创建人编码
     * @param code 编码
     */
    void setUpdateUserCode(String code);

}


