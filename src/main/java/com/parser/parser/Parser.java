package com.parser.parser;

import com.parser.ast.ExpressionNode;
import com.parser.ast.FuncNode;
import com.parser.ast.Node;
import com.parser.ast.NumberNode;
import com.parser.lexer.Lexer;
import com.parser.lexer.Token;
import com.parser.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

class Parser {
    private List<Token> tokens;
    private int index;
    private int length;

    Parser(Lexer lexer) {
        tokens = lexer.run();
        this.index = 0;
        this.length = tokens.size();
    }

    Parser(String source) {
        this(new Lexer(source));
    }

    Node run() {
        return expression();
    }

    private Node factor() {
        if (receive(TokenType.LEFT_PAREN)) {
            Node expression = expression();
            expect(TokenType.RIGHT_PAREN);
            return expression;
        } else if (receive(TokenType.ID)) {
            prev();
            return fun();
        } else {
            return number();
        }
    }

    private Node term() {
        Node left = factor();
        if (isTimesOrDivide()) {
            char op = operator();
            Node right = term();
            return expression(left, right, op);
        }
        return left;
    }

    private Node expression() {
        Node left = term();

        if (isPlusOrMinus()) {
            char op = operator();
            Node right = expression();
            return expression(left, right, op);
        }
        return left;
    }

    private boolean isPlusOrMinus() {
        return !eof() &&
                (current().getTokenType().equals(TokenType.PLUS) || current().getTokenType().equals(TokenType.MINUS));
    }

    private boolean isTimesOrDivide() {
        return !eof() &&
                (current().getTokenType().equals(TokenType.TIMES) || current().getTokenType().equals(TokenType.DIVIDE));
    }

    private char operator() {
        return next().getObject().toString().charAt(0);
    }

    private Node fun() {
        String funcName = next().getObject().toString();
        expect(TokenType.LEFT_PAREN);
        List<Node> args = new ArrayList<>();
        while (!receive(TokenType.RIGHT_PAREN)) {
            if (!receive(TokenType.COMMA)) {
                args.add(expression());
            }
        }
        return new FuncNode(funcName, args);
    }

    private Node number() {
        return new NumberNode((Integer) next().getObject());
    }

    private Node expression(Node left, Node right, char op) {
        return new ExpressionNode(left, right, op);
    }

    private void expect(TokenType tokenType) {
        if (!receive(tokenType)) {
            throw new RuntimeException("unexpected token type");
        }
    }

    private boolean receive(TokenType tokenType) {
        if (!eof() && current().getTokenType().equals(tokenType)) {
            next();
            return true;
        }
        return false;
    }

    private boolean eof() {
        return index >= length;
    }

    private Token current() {
        if (!eof()) return tokens.get(index);
        throw new RuntimeException("out of tokens index.");
    }

    private Token next() {
        Token current = current();
        index++;
        return current;
    }

    private Token prev() {
        Token current = current();
        index--;
        return current;
    }
}
