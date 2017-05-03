package com.parser.ast;

import com.parser.parser.Visitor;

public class ExpressionNode implements Node {
    public Node left;
    public Node right;
    public char operator;

    public ExpressionNode(Node left, Node right, char operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }


    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpressionNode that = (ExpressionNode) o;

        if (operator != that.operator) return false;
        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        return right != null ? right.equals(that.right) : that.right == null;

    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        result = 31 * result + (int) operator;
        return result;
    }

    @Override
    public String toString() {
        return "ExpressionNode{" +
                "left=" + left +
                ", right=" + right +
                ", operator=" + operator +
                '}';
    }
}
