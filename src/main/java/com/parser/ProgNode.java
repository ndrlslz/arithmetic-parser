package com.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ProgNode extends Node {
    private List<Node> prog;

    public ProgNode() {
        super(NodeType.PROG);
        prog = new ArrayList<>();
    }

    public void addNode(Node node) {
        prog.add(node);
    }

    @Override
    public String toString() {


        StringBuilder stringBuilder = new StringBuilder();
        prog.forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                stringBuilder.append(node);
            }
        });

        return stringBuilder.toString();
    }
}
