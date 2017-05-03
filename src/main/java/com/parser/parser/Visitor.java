package com.parser.parser;

import com.parser.ast.ExpressionNode;
import com.parser.ast.Node;
import com.parser.ast.NumberNode;

public interface Visitor<T> {
    T visit(Node node);
    T visit(NumberNode numberNode);
    T visit(ExpressionNode expressionNode);
}
