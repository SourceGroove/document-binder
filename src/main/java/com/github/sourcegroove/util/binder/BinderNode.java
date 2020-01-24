package com.github.sourcegroove.util.binder;

import java.util.List;

public interface BinderNode {
    String getPath();
    String getName();
    BinderNode getParent();
    void setParent(BinderNode parent);
    List<BinderNode> getChildren();

}
