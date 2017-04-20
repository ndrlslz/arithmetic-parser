package com.parser;

public interface Visitor<T> {
    T visit(Node node);
    T visit(NumberNode numberNode);
    T visit(ExpressionNode expressionNode);
}
