public class Lexer {
    private InputStream inputStream;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }



    public String readNext() {
        StringBuilder buffer = new StringBuilder();
        boolean newWord = false;

        while (!inputStream.eof()) {
            char current = inputStream.peek();
            if (current == ' ' && newWord) {
                inputStream.next();
                return buffer.toString();
            } else if (current == '\n') {
                if (newWord) {
                    return buffer.toString();
                } else {
                    inputStream.next();
                    return "\n";
                }
            } else {
                inputStream.next();
                newWord = true;
                buffer.append(String.valueOf(current));
            }
        }
        return buffer.toString();
    }

}
