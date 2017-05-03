package com.parser.lexer;

public class InputStream {
    private String input;
    private int index;
    private int line;
    private int col;
    private int length;


    InputStream(String input) {
        this.input = input;
        this.length = input.length();
    }

    char peek() {
        return input.charAt(index);
    }

    boolean eof() {
        return index == length;
    }

    char next() {
        char c = input.charAt(index++);
        if ('\n' == c) {
            line++;
            col = 0;
        } else {
            col++;
        }
        return c;
    }

}
