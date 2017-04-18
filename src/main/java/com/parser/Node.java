package com.parser;

public abstract class Node {
    private NodeType nodeType;

    public Node(NodeType nodeType) {
        this.nodeType = nodeType;
    }


    @Override
    public String toString() {
        return "Node{" +
                "nodeType=" + nodeType +
                '}';
    }
}
