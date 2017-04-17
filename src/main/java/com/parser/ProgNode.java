package com.parser;

import java.util.ArrayList;
import java.util.List;

public class ProgNode extends Node {
    private List<Node> prog;

    public ProgNode() {
        super(NodeType.PROG);
        prog = new ArrayList<>();
    }

    public void addNode(Node node) {
        prog.add(node);
    }
}
