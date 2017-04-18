package com.parser;

public class AssignNode extends Node {
    private String operator;
    private Node left;
    private Node right;

    public AssignNode(Object left, Object right) {
        super(NodeType.ASSIGN);
        operator = "=";
    }

    @Override
    public String toString() {
        return "AssignNode{" +
                "operator='" + operator + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
