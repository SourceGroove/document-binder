package com.github.sourcegroove.util.binder;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Binder {
    protected final Log log = LogFactory.getLog(getClass());
    public static final String PATH_SEPARATOR = "/";
    public static final int MAX_DEPTH = 5;
    private String name;
    private int numberOfDocuments = 0;
    private List<BinderNode> nodes = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public int getNumberOfDocuments() {
        return numberOfDocuments;
    }
    public void setNumberOfDocuments(int numberOfDocuments) { }

    public List<BinderNode> getNodes() {
        return Collections.unmodifiableList(this.nodes);
    }
    public void setNodes(List<BinderNode> nodes){ }

    public BinderFolder getFolderAtPath(String path){
        BinderNode node = getNodeAtPath(this.nodes, path);
        if(node instanceof BinderFolder){
            return (BinderFolder)node;
        } else {
            return null;
        }
    }

    public void add(BinderDocument document) {
        add(document, null);
    }

    public void add(BinderDocument document, String path) {
        if (document == null) {
            throw new IllegalArgumentException("Document must not be null");
        }
        if (StringUtils.isBlank(path)) {
            path = PATH_SEPARATOR;
        }
        BinderFolder parent = getFolderAtPath(path);
        if (parent == null) {
            parent = add(path);
        }

        numberOfDocuments++;
        parent.add(document);
    }
    public BinderFolder add(String path){
        String[] segments = StringUtils.split(path, PATH_SEPARATOR);
        if (segments.length > MAX_DEPTH) {
            throw new IllegalArgumentException("Path depth exceeds limit of " + MAX_DEPTH);
        }
        log.trace("Creating path " + path);
        BinderFolder parent = null;
        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            String absolutePath = parent != null ? parent.getPath() + PATH_SEPARATOR + segment : segment;

            log.trace("Checking for node at " + absolutePath);
            BinderNode node = getFolderAtPath(absolutePath);
            if (node != null) {
                log.trace("Node exists at path " + absolutePath);
                parent = (BinderFolder)node;
                continue;
            }

            log.trace("Creating new node at " + absolutePath);
            BinderFolder folder = new BinderFolder(segment);
            if (parent == null) {
                this.nodes.add(folder);
            } else {
                parent.add(folder);
            }
            parent = folder;
        }
        return parent;
    }

    private BinderNode getNodeAtPath(List<BinderNode> nodes, String path) {
        if (CollectionUtils.isEmpty(nodes) || StringUtils.isBlank(path)) {
            return null;
        }
        for (BinderNode n : nodes) {
            log.trace("Evaluating node at path '" + n.getPath() + "' for path '" + path + "'");
            if (StringUtils.equalsIgnoreCase(n.getPath(), path)) {
                log.trace("Found node '" + path + "', returning it");
                return n;

            } else if (CollectionUtils.isNotEmpty(n.getChildren())) {
                BinderNode node = getNodeAtPath(n.getChildren(), path);
                if (node != null) {
                    log.trace("Found node '" + path + "', returning it");
                    return node;
                }
            }
        }
        log.trace("Path '" + path + "' not found");
        return null;
    }

}
