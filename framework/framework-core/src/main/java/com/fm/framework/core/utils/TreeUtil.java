package com.fm.framework.core.utils;



import com.fm.framework.core.model.ITreeNode;
import com.fm.framework.core.model.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形工具
 * @author clufeng
 * @version 1.0.0
 **/
public class TreeUtil {

    public static boolean isNull4ParentId(ITreeNode node) {
        return node.getParentId() == null || node.getParentId() == -1;
    }

    public static <E> void setParentNull(TreeNode<E> node) {
        node.setParentNode(null);
        if (node.isLeaf()) {
            node.setParentNode(null);
        } else {
            node.getChilds().forEach(TreeUtil::setParentNull);
        }
    }

    /**
     * 构建树
     *
     * @param values 需要组建树的数据List
     * @param <T>    数据类型
     * @return 树的根节点
     */
    public static <T extends ITreeNode> TreeNode<T> buildTree(List<T> values) {

        TreeNode<T> root = new TreeNode<>(null, null);

        Map<Long, TreeNode<T>> nodeMap = new HashMap<>();
        List<T> emptyValueList = new ArrayList<>();

        for (T value : values) {

            TreeNode<T> node;

            if (isNull4ParentId(value)) {

                node = new TreeNode<>(root, value);

                root.addChild(node);

            } else {

                TreeNode<T> parentNode = nodeMap.get(value.getParentId());

                if (parentNode == null) {
                    emptyValueList.add(value);
                    node = new TreeNode<>(null, value);
                } else {
                    node = new TreeNode<>(parentNode, value);
                    parentNode.addChild(node);
                }
            }

            nodeMap.put(value.getNodeId(), node);

        }
        /*
         *
         */
        if (!emptyValueList.isEmpty()) {

            for (T value : emptyValueList) {

                TreeNode<T> node;

                TreeNode<T> parentNode = nodeMap.get(value.getParentId());

                node = nodeMap.get(value.getNodeId());

                if (parentNode == null) {

                    if (node == null) {
                        node = new TreeNode<>(root, value);
                    }

                    root.addChild(node);

                } else {

                    if (node == null) {
                        node = new TreeNode<>(parentNode, value);
                    }

                    parentNode.addChild(node);

                    node.setParentNode(parentNode);

                }
            }
        }

        return root;
    }

}
