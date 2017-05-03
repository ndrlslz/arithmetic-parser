package com.parser.ast;

import com.parser.parser.Visitor;

public class NumberNode implements Node {
    private int number;

    public NumberNode(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberNode that = (NumberNode) o;

        return number == that.number;

    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "number=" + number +
                '}';
    }
}
