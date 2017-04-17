package com.parser;

import java.util.List;

public class LambdaNode extends Node {
    private List vars;
    private Node body;

    public LambdaNode(List vars, Node body) {
        super(NodeType.LAMBDA);
        this.vars = vars;
        this.body = body;
    }
}
