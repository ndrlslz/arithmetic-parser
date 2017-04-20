package com.parser;

import java.util.List;

public class FuncNode implements Node {
    private String funcName;
    private List<Node> args;

    public FuncNode(String funcName, List<Node> args) {
        this.funcName = funcName;
        this.args = args;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return null;
    }

    @Override
    public String toString() {
        return "FuncNode{" +
                "funcName='" + funcName + '\'' +
                ", args=" + args +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuncNode funcNode = (FuncNode) o;

        if (funcName != null ? !funcName.equals(funcNode.funcName) : funcNode.funcName != null) return false;
        return args != null ? args.equals(funcNode.args) : funcNode.args == null;

    }

    @Override
    public int hashCode() {
        int result = funcName != null ? funcName.hashCode() : 0;
        result = 31 * result + (args != null ? args.hashCode() : 0);
        return result;
    }
}
