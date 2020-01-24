package com.github.sourcegroove.util.binder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.github.sourcegroove.util.binder.Binder.PATH_SEPARATOR;

public class BinderDocument implements BinderNode  {

    private BinderNode parent;
    private String name;
    private String description;
    private Long id;

    public BinderDocument(){
    }
    public BinderDocument(String name){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = StringUtils.replace(name, PATH_SEPARATOR, "_");
    }
    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return this.id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BinderNode getParent() {
        return this.parent;
    }
    public void setParent(BinderNode parent) {
        this.parent = parent;
    }
    public List<BinderNode> getChildren() {
        return null;
    }

    public String getPath(){
        return this.parent != null ? this.parent.getPath() + PATH_SEPARATOR + this.name : this.name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
