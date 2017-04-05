import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LexerTest {
    private Lexer lexer;

    @Before
    public void setUp() {
        lexer = new Lexer(new InputStream("123 + 456\n123"));
    }
    @Test
    public void readNextTest() {
        assertEquals(lexer.readNext1(), "123");
        assertEquals(lexer.readNext1(), "+");
        assertEquals(lexer.readNext1(), "456");
        assertEquals(lexer.readNext1(), "\n");
        assertEquals(lexer.readNext1(), "123");
    }
}