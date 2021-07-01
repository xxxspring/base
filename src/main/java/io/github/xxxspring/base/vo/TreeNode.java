package io.github.xxxspring.base.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ace on 2017/6/12.
 */
public class TreeNode<T> {
    protected int id;
    protected int parentId;
    protected T data;

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    List<TreeNode> children = new ArrayList<TreeNode>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public T getData() {
        return data;
    }

    public void setData(T t) {
        this.data = t;
    }

    public void add(TreeNode node){
        children.add(node);
    }
}
