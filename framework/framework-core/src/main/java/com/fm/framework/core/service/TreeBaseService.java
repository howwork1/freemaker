package com.fm.framework.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.model.DBFieldConst;
import com.fm.framework.core.model.ITreeNode;
import com.fm.framework.core.utils.TreeIncodeUtil;
import com.fm.framework.core.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 树形结构通用服务
 * @author clufeng
 * @version 1.0.0
 **/
@Slf4j
public class TreeBaseService<M extends BaseMapper<T>, T extends BaseModel & ITreeNode> extends BaseService<M, T> {

    /**
     * 获取当前节点
     * @param parent 当前节点，也就是父节点
     * @return 返回下一级子节点
     */
    public List<T> findChild(T parent) {

        return findChild(parent.getId());
    }

    /**
     * 获取当前节点
     *
     * @param nodeId 当前节点，也就是父节点
     * @return 返回下一级子节点
     */
    public List<T> findChild(Long nodeId) {

        QueryWrapper<T> queryWrapper = getQueryWrapper().eq(DBFieldConst.PARENT_ID, nodeId);

        List<T> childs = getBaseMapper().selectList(queryWrapper);

        log.debug("获取当前节点：{}, 子节点 : {}", nodeId, childs);

        return childs;
    }


    /**
     * 获取当前节点下所有子节点，切记，数量不宜太多，如果太多建议调用{@link TreeBaseService#findChild(BaseModel ITreeNode)}
     * @param parent 当前节点，也就是父节点
     * @return 返回所有子节点
     */
    public List<T> findAllChild(T parent) {

        QueryWrapper<T> queryWrapper = getQueryWrapper()
                .likeRight(DBFieldConst.INCODE, parent.getIncode())
                .ne(DBFieldConst.INCODE, parent.getIncode());

        List<T> childs = getBaseMapper().selectList(queryWrapper);

        log.debug("获取当前节点：{}, 所有子节点 : {}", parent, childs);

        return childs;
    }

    /**
     * 获取当前节点下所有子节点，切记，数量不宜太多，如果太多建议调用{@link TreeBaseService#findChild(BaseModel ITreeNode)}
     * @param parentId 当前节点ID，也就是父节点
     * @return 返回所有子节点
     */
    public List<T> findAllChild(Long parentId) {

        if(Objects.isNull(parentId)) {
            return new ArrayList<>();
        }

        return findAllChild(get(parentId));
    }

    public List<T> findAllChildByNode(T node) {
        return findAllChildByNodeId(node.getId());
    }

    public List<T> findAllChildByNodeId(Long nodeId) {
        List<T> childs = findChild(nodeId);

        List<T> result = new ArrayList<>(childs);

        if (!childs.isEmpty()) {
            for (T child : childs) {
                result.addAll(findAllChildByNodeId(child.getId()));
            }
        }

        return result;
    }


    /**
     * 设置内码
     * @param node 当前要设置的节点
     */
    protected void setIncode(T node) {
        String parentIncode = null;
        if(!TreeUtil.isNull4ParentId(node)) {
            T parent = getBaseMapper().selectById(node.getParentId());
            log.debug("查询父组织信息: {}", parent);
            parentIncode = parent.getIncode();
        }

        String incode;

        do {
            incode = TreeIncodeUtil.create(parentIncode);

        } while (this.getBaseMapper().selectCount(getQueryWrapper().eq(DBFieldConst.INCODE, incode)) > 0);

        node.setIncode(incode);
    }

    @Override
    protected void beforeSave(T model) {
        super.beforeSave(model);
        if(Objects.isNull(model.getIncode())) {
            setIncode(model);
        }
    }

    protected void beforeSave(Collection<T> models) {
        super.beforeSave(models);
        models.forEach(model -> {
            if(Objects.isNull(model.getIncode())) {
                setIncode(model);
            }
        });
    }

    /**
     * 补全父节点
     * @param nodes 子节点集合
     */
    public void completionParentNode(List<T> nodes) {

        if(!nodes.isEmpty()) {

            Set<Long> needCompletion = new HashSet<>();

            do {

                needCompletion.clear();

                Set<Long> menuIdSet = nodes.stream().map(T::getId).collect(Collectors.toSet());

                nodes.stream().filter(menu -> menu.getParentId() != -1).forEach(menu -> {
                    if(!menuIdSet.contains(menu.getParentId())) {
                        needCompletion.add(menu.getParentId());
                    }
                });

                if(!needCompletion.isEmpty()) {
                    nodes.addAll(getByIds(new ArrayList<>(needCompletion)));
                }

            } while (!needCompletion.isEmpty());
        }
    }

    public void updateIncode(String parentIncode, List<T> nodes) {

        for (T node : nodes) {
            node.setIncode(TreeIncodeUtil.create(parentIncode));

            UpdateWrapper<T> updateWrapper = Wrappers.update();

            updateWrapper.set(DBFieldConst.INCODE, node.getIncode()).eq(DBFieldConst.ID, node.getId());

            this.getBaseMapper().update(null, updateWrapper);

            List<T> childs = findChild(node.getId());

            if(!childs.isEmpty()) {
                updateIncode(node.getIncode(), childs);
            }

        }

    }


}
