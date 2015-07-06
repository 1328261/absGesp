import java.util.ArrayDeque;
import java.util.Queue;

import java.io.InputStream;
import java.io.InputStreamReader;

public class AtomScanner {

    private Queue<Token> tokens;

    public AtomScanner(InputStream in) throws LexerException {
        try {
            tokens = new ArrayDeque<Token>();

            Lexer lexer = new Lexer(new InputStreamReader(in));
            for (Token token : lexer) {
                tokens.add(token);
            }
        } catch (LexerError le) {
            throw new LexerException(le.getPosition(), le.getMessage());
        }
    }

    public boolean hasNext() {
        return tokens.size() > 0 && tokens.peek().type != Token.Type.EOF;
    }

    public String next() {
        return tokens.remove().data;
    }

    public boolean hasNextInteger() {
        return hasNext() && tokens.peek().type == Token.Type.INT;
    }

    public boolean hasNextAtom() {
        return hasNext() && tokens.peek().type == Token.Type.ATOM;
    }

    public boolean hasNextString() {
        return hasNext() && tokens.peek().type == Token.Type.STR;
    }

    public int nextInteger() {
        return Integer.parseInt(tokens.remove().data);
    }

    public String nextAtom() {
        return tokens.remove().data;
    }

    public String nextString() {
        return tokens.remove().data;
    }

}
