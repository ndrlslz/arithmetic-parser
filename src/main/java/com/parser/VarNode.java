package com.parser;

public class VarNode extends Node {
    private Object var;

    public VarNode() {
        super(NodeType.VAR);
    }


    @Override
    public String toString() {
        return "VarNode{" +
                "var=" + var +
                '}';
    }
}
