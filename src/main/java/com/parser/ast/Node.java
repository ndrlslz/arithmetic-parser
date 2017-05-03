package com.parser.ast;

import com.parser.parser.Visitor;

public interface Node {
    <T> T accept(Visitor<T> visitor);
}
