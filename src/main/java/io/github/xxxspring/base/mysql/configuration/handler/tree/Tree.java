package io.github.xxxspring.base.mysql.configuration.handler.tree;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * tree TODO <br>
 *
 * @author kangxu2 2017-1-7
 *
 */
public class Tree<T> {
    /**
     * 节点ID
     */
    private String itemtype_id;
    /**
     * 显示节点文本
     */
    private String itemtype_name;
    /**
     * 节点状态，open closed
     */
    private String state = "open";
    /**
     * 节点是否被选中 true false
     */
    private boolean checked = false;
    /**
     * 节点属性
     */
    private List<Map<String, Object>> attributes;
    /**
     * 节点的子节点
     */
    private List<Tree<T>> children = new ArrayList<Tree<T>>();

    /**
     * 父ID
     */
    private String parent_itemtypeid;
    /**
     * 是否有父节点
     */
    private boolean isParent = false;
    /**
     * 是否有子节点
     */
    private boolean isChildren = false;



    public String getItemtype_id() {
        return itemtype_id;
    }

    public void setItemtype_id(String itemtype_id) {
        this.itemtype_id = itemtype_id;
    }

    public String getItemtype_name() {
        return itemtype_name;
    }

    public void setItemtype_name(String itemtype_name) {
        this.itemtype_name = itemtype_name;
    }

    public String getParent_itemtypeid() {
        return parent_itemtypeid;
    }

    public void setParent_itemtypeid(String parent_itemtypeid) {
        this.parent_itemtypeid = parent_itemtypeid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Map<String, Object>> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Map<String, Object>> attributes) {
        this.attributes = attributes;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isChildren() {
        return isChildren;
    }

    public void setChildren(boolean isChildren) {
        this.isChildren = isChildren;
    }


    public Tree(String itemtype_id, String itemtype_name, String state, boolean checked,
                List<Map<String, Object>> attributes, List<Tree<T>> children,
                boolean isParent, boolean isChildren, String parent_itemtypeid) {
        super();
        this.itemtype_id = itemtype_id;
        this.itemtype_name = itemtype_name;
        this.state = state;
        this.checked = checked;
        this.attributes = attributes;
        this.children = children;
        this.isParent = isParent;
        this.isChildren = isChildren;
        this.parent_itemtypeid = parent_itemtypeid;
    }

    public Tree() {
        super();
    }

    @Override
    public String toString() {

        return JSON.toJSONString(this);
    }

}


