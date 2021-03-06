package com.parser.parser;

import com.parser.ast.ExpressionNode;
import com.parser.ast.FuncNode;
import com.parser.ast.Node;
import com.parser.ast.NumberNode;
import com.parser.lexer.Lexer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void parse() throws Exception {
        Node node = new Parser(new Lexer("1 + 2 + 3 * 4 + 5 / 6")).run();
        System.out.println(node);

        assertEquals(node, expression(
                number(1),
                '+',
                expression(
                        number(2),
                        '+',
                        expression(
                                expression(
                                        number(3),
                                        '*',
                                        number(4)),
                                '+',
                                expression(
                                        number(5),
                                        '/',
                                        number(6))))));

    }

    @Test
    public void parseParen() throws Exception {

        Node node = new Parser(new Lexer("(1+2)*3")).run();

        assertEquals(node, expression(
                expression(
                        number(1),
                        '+',
                        number(2)
                ),
                '*',
                number(3)));
    }

    private Node number(int num) {
        return new NumberNode(num);
    }

    private Node fun(String name, List<Node> args) {
        return new FuncNode(name, args);
    }

    private Node expression(Node left, char op, Node right) {
        return new ExpressionNode(left, right, op);
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


}