package com.fm.framework.core.service;



import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.model.ITreeNode;

import java.util.List;

/**
 * <p>树形节点服务</p>
 *
 * @author clufeng
 */
public interface TreeService<T extends BaseModel & ITreeNode> extends Service<T>{

    List<T> findChild(T parent);
    /**
     * 获取当前节点下所有子节点，切记，数量不宜太多，如果太多建议调用{@link TreeBaseService#findAllChild(BaseModel ITreeNode)}
     * @param parent 当前节点，也就是父节点
     * @return 返回所有子节点
     */
    List<T> findAllChild(T parent);
    /**
     * 获取当前节点下所有子节点，切记，数量不宜太多，如果太多建议调用{@link TreeBaseService#findAllChild(BaseModel ITreeNode)}
     * @param parentId 当前节点ID，也就是父节点
     * @return 返回所有子节点
     */
    List<T> findAllChild(Long parentId);

    /**
     * 补全给定节点的父节点信息
     * @param nodes 给定节点
     */
    public void completionParentNode(List<T> nodes);
}
