package com.parser;

import org.junit.Before;
import org.junit.Test;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ParserTest {
    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Parser(new Lexer("(1 + 2) * 3"));
    }

    @Test
    public void parse() throws Exception {
        Node node = parser.expression();
        System.out.println(node);
    }


//    @Test
//    public void current() throws Exception {
//        assertEquals(parser.current(), number(1));
//    }
//
//    @Test
//    public void next() throws Exception {
//        parser.next();
//        assertEquals(parser.next(), symbol("+"));
//        assertEquals(parser.next(), number(2));
//        assertEquals(parser.next(), symbol("*"));
//    }
//
//    @Test
//    public void prev() throws Exception {
//        parser.next();
//        parser.next();
//        parser.prev();
//        assertEquals(parser.current(), symbol("+"));
//    }

    private Token number(int num) {
        return new Token(TokenType.NUMBER, num);
    }

    public Token symbol(String input) {
        switch (input) {
            case "+": return new Token(TokenType.PLUS, input);
            case "-": return new Token(TokenType.MINUS, input);
            case "*": return new Token(TokenType.TIMES, input);
            case "/": return new Token(TokenType.DIVIDE, input);
            case "(": return new Token(TokenType.LEFT_PAREN, input);
            case ")": return new Token(TokenType.RIGHT_PAREN, input);
            default:
                throw new RuntimeException("unexpected!");
        }
    }
}