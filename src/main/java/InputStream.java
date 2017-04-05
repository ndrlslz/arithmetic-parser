public class InputStream {
    private String input;
    private int index;
    private int line;
    private int col;
    private int length;

    public int getIndex() {
        return index;
    }

    public InputStream(String input) {
        this.input = input;
        this.length = input.length();
    }

    public char peek() {
        return input.charAt(index);
    }

    public boolean eof() {
        return index == length;
    }

    public char next() {
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
