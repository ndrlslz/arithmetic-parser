import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class InputStreamTest {
    private InputStream inputStream;

    @Before
    public void setUp() throws Exception {
        inputStream = new InputStream("1 + 2\n");
    }

    @Test
    public void peek() throws Exception {
        assertEquals('1', inputStream.peek());
        inputStream.next();
        inputStream.next();
        assertEquals('+', inputStream.peek());
        inputStream.next();
        inputStream.next();
        assertEquals('2', inputStream.peek());
    }

    @Test
    public void eof() throws Exception {
        IntStream.range(0, 6).forEach(value -> inputStream.next());
        assertTrue(inputStream.eof());
    }

    @Test
    public void next() throws Exception {
        assertEquals('1', inputStream.next());
        assertEquals(' ', inputStream.next());
        assertEquals('+', inputStream.next());
        assertEquals(' ', inputStream.next());
        assertEquals('2', inputStream.next());
    }

}