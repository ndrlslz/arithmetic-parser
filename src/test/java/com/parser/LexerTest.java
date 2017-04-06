package com.parser;

import org.junit.Before;
import org.junit.Test;

import static com.parser.Lexer.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LexerTest {
    private Lexer lexer;

    @Before
    public void setUp() {
        lexer = new Lexer(new InputStream("sum = lambda(a, b) {\n" +
                "  a + b;\n" +
                "};"));
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
    public void testRead() {
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

}