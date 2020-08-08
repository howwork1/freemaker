package com.fm.framework.core.model;

/**
 * 实体对象支持树形接口
 * @author clufeng
 */
public interface ITreeNode {

    /**
     * 返回当前树节点ID
     * @return 树节点ID
     */
    Long getNodeId();

    /**
     * 返回当前树节点父级节点ID
     * @return 父级节点ID
     */
    Long getParentId();


    /**
     * 获取树形内码数据
     * @return
     */
    String getIncode();

    /**
     * 设置树形内码
     */
    void setIncode(String incode);

}
