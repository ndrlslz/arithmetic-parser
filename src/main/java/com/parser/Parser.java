package com.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Parser {
    private Lexer lexer;
    private Map<String, Integer> PRECEDENCE = new HashMap<>();

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        PRECEDENCE.put("=", 1);
        PRECEDENCE.put("||", 2);
        PRECEDENCE.put("&&", 3);
        PRECEDENCE.put("<", 7);
        PRECEDENCE.put(">", 7);
        PRECEDENCE.put("<=", 7);
        PRECEDENCE.put("+", 10);
        PRECEDENCE.put("-", 10);
        PRECEDENCE.put("*", 20);
        PRECEDENCE.put("/", 20);
        PRECEDENCE.put("%", 20);
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

    private boolean isOp() {
        Token current = lexer.peek();
        return current.getTokenType().equals(TokenType.OPERATION);
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

    public Object parseExpression() {
        return maybe_call(() -> maybe_binary(parseAtom(), 0));
    }

    public ProgNode parseTopLevel() {
        ProgNode progNode = new ProgNode();
        while (!lexer.eof()) {
            progNode.addNode((Node) parseExpression());
            if (!lexer.eof()) skipPunc(";");
        }
        return progNode;
    }

    public ProgNode parseProg() {
        List<Object> progs = delimited("{", "}", ";", (ParserToken) parseExpression());
        ProgNode progNode = new ProgNode();
        progs.forEach(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                progNode.addNode((Node) o);
            }
        });
        return progNode;
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
        Node body = null;
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


    private Object maybe_call(ParserToken expression) {
        Object expr = expression.parse();
        return isPunc("(") ? parseCall(expr) : expr;
    }

    private Object maybe_binary(Object left, int myPrec) {
        Token current = lexer.peek();
        if (isOp()) {
            int hisPrec = PRECEDENCE.get(current.getObject().toString());
            if (hisPrec > myPrec) {
                lexer.next();
                if (current.getObject().toString().equals("=")) {
                    AssignNode assignNode = new AssignNode(left, maybe_binary(parseAtom(), hisPrec));
                    return maybe_binary(assignNode, myPrec);
                }
            }
        }
        return left;
    }
    private Object parseAtom() {
        if (isPunc("(")) {
            lexer.next();
            Object exp = parseExpression();
            skipPunc(")");
            return exp;
        }
        if (isLambda()) {
            lexer.next();
            return parseLambda();
        }
        Token token = lexer.next();
        if (token.getTokenType().equals(TokenType.VAR)
                || token.getTokenType().equals(TokenType.NUMBER)
                || token.getTokenType().equals(TokenType.STRING)) {
            return token;
        }
        throw new RuntimeException("unexpected!");
    }

    private CallNode parseCall(Object func) {
        return new CallNode((Node) func, delimited("(", ")", ",", (ParserToken) parseExpression()));
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
