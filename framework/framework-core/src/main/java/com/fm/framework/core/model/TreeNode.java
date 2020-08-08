package com.fm.framework.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 * @author clufeng
 * @version 1.0.0
 **/
public class TreeNode<E> {

    private TreeNode<E> parentNode;

    private E value;

    private List<TreeNode<E>> childs = new ArrayList<>();

    public TreeNode(TreeNode<E> parentNode, E value) {
        this.value = value;
        this.parentNode = parentNode;
    }

    public void addChild(TreeNode<E> node) {
        childs.add(node);
    }

    public boolean isLeaf() {
        return childs.isEmpty();
    }

    public TreeNode<E> getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode<E> parentNode) {
        this.parentNode = parentNode;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public List<TreeNode<E>> getChilds() {
        return childs;
    }

    public void setChilds(List<TreeNode<E>> childs) {
        this.childs = childs;
    }
}
