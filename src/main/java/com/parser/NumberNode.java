package com.parser;

public class NumberNode extends Node {
    private Integer number;

    public NumberNode() {
        super(NodeType.NUM);
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "number=" + number +
                '}';
    }
}
