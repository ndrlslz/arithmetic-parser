package com.parser;

import java.util.List;

public class CallNode extends Node{
    private NodeType nodeType;
    private Node func;
    private List args;


    public CallNode(Node func, List args) {
        super(NodeType.CALL);
        this.func = func;
        this.args = args;
    }

    @Override
    public String toString() {
        return "CallNode{" +
                "nodeType=" + nodeType +
                ", func=" + func +
                ", args=" + args +
                '}';
    }
}
