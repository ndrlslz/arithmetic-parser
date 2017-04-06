package com.parser;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Lexer {
    private final static Pattern ID_PATTERN = Pattern.compile("[a-zλ_]");
    private final static List KEY_WORDS = Arrays.asList("if", "else", "lambda", "then");
    private InputStream inputStream;
    private Token current;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    static Token number(Integer num) {
        return new Token(TokenType.NUMBER, num);
    }

    static Token string(String s) {
        return new Token(TokenType.STRING, s);
    }

    static Token punctuation(String p) {
        return new Token(TokenType.PUNCTUATION, p);
    }

    static Token keyWord(String kw) {
        return new Token(TokenType.KEY_WORD, kw);
    }

    static Token var(String var) {
        return new Token(TokenType.VAR, var);
    }

    static Token operator(String operator) {
        return new Token(TokenType.OPERATION, operator);
    }

    boolean isWhiteSpace(char c) {
        return " \t\n".indexOf(c) >= 0;
    }

    boolean isNum(char c) {
        return c >= 48 && c <= 57;
    }

    boolean isPunctuation(char c) {
        return ",;(){}[]".indexOf(c) >= 0;
    }

    boolean isOperator(char c) {
        return "+-*/%=&|<>!".indexOf(c) >= 0;
    }

    private boolean isIdStart(char c) {
        return ID_PATTERN.matcher(String.valueOf(c)).find();
    }

    private boolean isId(char c) {
        return isIdStart(c) || "?!-<>=0123456789".indexOf(c) >= 0;
    }

    boolean isKeyWord(String s) {
        return KEY_WORDS.contains(s);
    }

    private String readWhile(Predicate<Character> predicate) {
        StringBuilder sb = new StringBuilder();
        while (!inputStream.eof() && predicate.test(inputStream.peek())) {
            sb.append(inputStream.next());
        }
        return sb.toString();
    }

    Token readNumber() {
        return number(Integer.valueOf(readWhile(this::isNum)));
    }

    private Token readPunctuation() {
        return punctuation(readWhile(this::isPunctuation));
    }

    private Token readOperator() {
        return operator(readWhile(this::isOperator));
    }

    private Token readString() {
        return string(readEscaped('"'));
    }

    private String readEscaped(char end) {
        boolean escaped = false;
        inputStream.next();
        StringBuilder sb = new StringBuilder();
        while (!inputStream.eof()) {
            char current = inputStream.next();
            if (escaped) {
                sb.append(current);
                escaped = false;
            } else if (current == '\\') {
                escaped = true;
            } else if (current == end) {
                break;
            } else {
                sb.append(current);
            }
        }
        return sb.toString();
    }

    Token readNext() {
        readWhile(this::isWhiteSpace);
        if (inputStream.eof()) return null;
        char current = inputStream.peek();
        if (isNum(current)) return readNumber();
        else if (isOperator(current)) return readOperator();
        else if (isPunctuation(current)) return readPunctuation();
        else if (isQuote(current)) return readString();
        else if (isIdStart(current)) {
            String s = readWhile(this::isId);
            if (isKeyWord(s)) {
                return keyWord(s);
            } else {
                return var(s);
            }
        }
        throw new RuntimeException("cannot handle char: " + current);
    }

    private boolean isQuote(char c) {
        return c == '"';
    }

    public Token peek() {
        if (current == null) {
            current = readNext();
        }
        return current;
    }

    Token next() {
        Token tok = current;
        current = null;
        if (tok == null) {
            return readNext();
        }
        return tok;
    }

    public boolean eof() {
        return peek() == null;
    }

}
