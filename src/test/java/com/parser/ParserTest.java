package com.parser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    private Parser parser;

    @Before
    public void setUp() {
//        parser = new Parser(new Lexer(new InputStream("lambda(a, b) {\n" +
//                "  a + b;\n" +
//                "};")));

        parser = new Parser(new Lexer("var a = 1;"));
    }
    @Test
    public void parseNode() throws Exception {
//        parser.parseNode();
        ProgNode progNode = parser.parseTopLevel();
        System.out.println(progNode.toString());
    }

}