package com.parser.lexer;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.parser.lexer.Lexer.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LexerTest {
    private Lexer lexer;

    @Before
    public void setUp() {
        lexer = new Lexer("1 + 2 * 3");
    }

    @Test
    public void testFun() {
        lexer = new Lexer("max(1, 2)");
        System.out.println(lexer.run());
    }

    @Test
    public void testPlus() {
        List<Token> result = lexer.run();
        assertEquals(result, Arrays.asList(number(1), symbol("+"), number(2), symbol("*"), number(3)));

    }

    @Test
    public void testIsWhiteSpace() throws Exception {
        assertTrue(lexer.isWhiteSpace(' '));
        assertTrue(lexer.isWhiteSpace('\t'));
        assertTrue(lexer.isWhiteSpace('\n'));
    }

    @Test
    public void testIsNum() {
        assertTrue(lexer.isNum('0'));
    }

    @Test
    public void testIsKeyWord() {
        assertTrue(lexer.isKeyWord("if"));
        assertTrue(lexer.isKeyWord("lambda"));
    }

    @Test
    public void testIsOp() {
        assertTrue(lexer.isOperator('>'));
        assertTrue(lexer.isOperator('='));
    }

    @Test
    public void testIsPunc() {
        assertTrue(lexer.isPunctuation('{'));
        assertTrue(lexer.isPunctuation(';'));
    }

    @Test
    public void testReadLambda() {

        lexer = new Lexer("sum = lambda(a, b) {\n" +
                "  a + b;\n" +
                "};");

        assertEquals(var("sum"), lexer.next());
        assertEquals(operator("="), lexer.next());
        assertEquals(keyWord("lambda"), lexer.next());
        assertEquals(punctuation("("), lexer.next());
        assertEquals(var("a"), lexer.next());
        assertEquals(punctuation(","), lexer.next());
        assertEquals(var("b"), lexer.next());
        assertEquals(punctuation(")"), lexer.next());
        assertEquals(punctuation("{"), lexer.next());
    }

    private Token number(int input) {
        return new Token(TokenType.NUMBER, input);
    }

    private Token symbol(String input) {
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