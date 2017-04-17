package com.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    static LambdaNode lambdaNode(List vars, Node body) {
        return new LambdaNode(vars, body);
    }

    private boolean isLambda(Token token) {
        return token.getTokenType().equals(TokenType.KEY_WORD)
                && "lambda".equals(token.getObject());
    }

    private boolean isLambda() {
        Token current = lexer.peek();
        return current.getTokenType().equals(TokenType.KEY_WORD) && "lambda".equals(current.getObject());
    }

    private boolean isPunc(String input) {
        Token current = lexer.peek();
        return current.getTokenType().equals(TokenType.PUNCTUATION) && input.equals(current.getObject());
    }

    private Token skipPunc(String punc) {
        Token current = lexer.peek();
        if (punc.equals(current.getObject())) {
            return lexer.next();
        }
        throw new RuntimeException("unexpected token, expecting " + punc);
    }


    public ProgNode parse() {
        ProgNode progNode = new ProgNode();

        while (!lexer.eof()) {
            Token current = lexer.peek();
            if (isLambda(current)) {
                progNode.addNode(parseLambda());
            }
        }


        return new ProgNode();
    }


    public Node parseNode() {
        Token current = lexer.next();
        if (isLambda(current)) return parseLambda();


        return null;
    }

    public Node parseExpression() {
        return null;
    }

    public ParserToken parseVarname() {
        return () -> {
            Token token = lexer.next();
            if (TokenType.VAR.equals(token.getTokenType())) {
                return token.getObject().toString();
            }
            throw new RuntimeException("Expecting variable name");
        };
    }

    public LambdaNode parseLambda() {
//        readUntil(token -> token.getObject().equals("("));
//        List<Token> args = readUntil(token -> token.getObject().equals(")"), ",");
        List<Object> args = delimited("(", ")", ",", parseVarname());
        Node body = parseExpression();
        System.out.println(args);
        return new LambdaNode(args, body);
    }

    private List<Object> delimited(String start, String stop, String separator, ParserToken parser) {
        List<Object> result = new ArrayList<>();
        boolean first = true;

        skipPunc(start);
        while (!lexer.eof()) {
            if (isPunc(stop)) break;
            if (first) {
                first = false;
            } else {
                skipPunc(separator);
            }
            if (isPunc(stop)) break;
//            result.add(lexer.next());
            result.add(parser.parse());
        }
        skipPunc(stop);
        return result;
    }

    public List<Token> readUntil(Predicate<Token> predicate) {
        List<Token> result = new ArrayList<>();
        while (!lexer.eof() && !predicate.test(lexer.peek())) {
            result.add(lexer.next());
        }
        lexer.next();
        return result;
    }

    public List<Token> readUntil(Predicate<Token> predicate, String separator) {
        List<Token> result = new ArrayList<>();
        while (!lexer.eof() && !predicate.test(lexer.peek())) {
            if (lexer.peek().getObject().equals(separator)) {
                lexer.next();
            } else {
                result.add(lexer.next());
            }

        }
        lexer.next();
        return result;
    }

}
