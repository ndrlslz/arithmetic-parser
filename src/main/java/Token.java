import java.util.Objects;

public class Token {
    private TokenType tokenType;
    private Object object;

    public Token(TokenType tokenType, Object object) {
        this.tokenType = tokenType;
        this.object = object;
    }

}
