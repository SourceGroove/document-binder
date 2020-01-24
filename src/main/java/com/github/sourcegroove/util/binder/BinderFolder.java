package com.github.sourcegroove.util.binder;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BinderFolder implements BinderNode {
    private String name;
    private BinderNode parent;
    private List<BinderNode> children;

    public BinderFolder(){
    }
    public BinderFolder(String name){
        this.setName(name);
    }
    public void setParent(BinderNode parent){
        this.parent = parent;
    }
    public BinderNode getParent(){
        return this.parent;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = StringUtils.replace(name, Binder.PATH_SEPARATOR, "_");
    }
    public String getPath(){
        return this.parent != null ? this.parent.getPath() + Binder.PATH_SEPARATOR + this.name : this.name;
    }
    public List<BinderNode> getChildren(){
        return this.children;
    }
    public void add(BinderNode node){
        if(node == null){
            throw new IllegalArgumentException("Can't add a null node!");
        }
        if(node.getName() == null){
            throw new IllegalArgumentException("Can't add a node with an null name!");
        }
        if(getChildren() == null){
            this.children = new ArrayList<>();
        }
        node.setParent(this);
        getChildren().add(node);
    }

    @Override
    public String toString(){
        return this.name;
    }

}
