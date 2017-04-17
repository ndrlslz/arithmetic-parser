package com.parser;

public class Token {
    private TokenType tokenType;
    private Object object;

    public Token(TokenType tokenType, Object object) {
        this.tokenType = tokenType;
        this.object = object;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (tokenType != token.tokenType) return false;
        return object != null ? object.equals(token.object) : token.object == null;

    }

    @Override
    public int hashCode() {
        int result = tokenType != null ? tokenType.hashCode() : 0;
        result = 31 * result + (object != null ? object.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", object=" + object +
                '}';
    }
}
