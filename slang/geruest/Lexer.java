import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

public class Lexer implements Iterator<Token>, Iterable<Token> {

    private final static int EOF_CH = -1;
    private final static char EOL_CH = '\n';

    // ---

    private Reader input;
    private int ch;

    private boolean eos = false; // end of (token-)stream reached
    private int line = 1;
    private int column = 0;

    // ---

    public Lexer(Reader input) throws LexerException {
        try {
            this.input = input;
            consume();
        } catch (LexerError le) {
            throw new LexerException(le.getPosition(), le.getMessage());
        }
    }

    // Iterator
    public boolean hasNext() {
        return !eos;
    }

    // Iterator
    public Token next() {
        skip();
        return scan();
    }

    // Iterator: "optional"
    public void remove() {
        throw new UnsupportedOperationException();
    }

    // Iterable
    public Iterator<Token> iterator() {
        return this;
    }

    // ---

    private void consume() {
        try {
            ch = input.read();
            if (match(EOL_CH)) {
                ++line;
                column = 0;
            } else {
                ++column;
            }
        } catch (IOException ex) {
            ch = EOF_CH;
            ++column;
        }
    }

    private boolean match(int c) {
        return ch == c;
    }

    private boolean accept(int c) {
        if (match(c)) {
            consume();
            return true;
        }
        return false;
    }

    private void expect(int c) {
        if (!accept(c)) {
            error();
        }
    }

    private void error() {
        if (ch == EOF_CH) {
            throw new LexerError(mark(), "unexpected end of file");
        } else {
            throw new LexerError(mark(), "unexpected character: '" + (char)ch + "'");
        }
    }

    private SourcePosition mark() {
        return new SourcePosition(line, column);
    }

    private boolean matchRange(int lowerBound, int upperBound) {
        return ch >= lowerBound && ch <= upperBound;
    }

    private String collectRange(int lowerBound, int uppperBound) {
        StringBuilder result = new StringBuilder();
        while (matchRange(lowerBound, uppperBound)) {
            result.append((char)ch);
            consume();
        }
        return result.toString();
    }

    private String collectUntil(int except) {
        StringBuilder result = new StringBuilder();
        while (!match(EOF_CH) && !match(except)) {
            result.append((char)ch);
            consume();
        }
        return result.toString();
    }

    private String collectUntilWhitespace() {
        StringBuilder result = new StringBuilder();
        while (!match(EOF_CH) && !match(' ') && !match('\t') && !match('\r') && !match('\n')) {
            result.append((char)ch);
            consume();
        }
        return result.toString();
    }

    // ---

    private Token scan() {
        SourcePosition here = mark();

        if (match(EOF_CH)) {
            return scanEof(here);
        }

        if (match('"')) {
            return scanStr(here);
        }

        if (match('{')) {
            return scanBraceStr(here);
        }

        if (matchRange('0', '9')) {
            return scanInt(here);
        }

        return scanAtom(here);
    }

    private Token scanEof(SourcePosition here) {
        expect(EOF_CH);
        eos = true;
        return new Token(Token.Type.EOF, here);
    }

    private Token scanAtom(SourcePosition start) {
        return new Token(Token.Type.ATOM, collectUntilWhitespace(), start);
    }

    private Token scanInt(SourcePosition start) {
        return new Token(Token.Type.INT, collectRange('0', '9'), start);
    }

    private Token scanStr(SourcePosition start) {
        expect('"');
        Token str = new Token(Token.Type.STR, collectUntil('"'), start);
        expect('"');
        return str;
    }

    private Token scanBraceStr(SourcePosition start) {
        expect('{');
        Token str = new Token(Token.Type.STR, collectUntil('}'), start);
        expect('}');
        return str;
    }

    // -- whitespace and comments

    private void skip() {
        while (acceptWhitespace() || acceptComment()) {
            /* noop */
        }
    }

    private boolean acceptWhitespace() {
        return accept(' ') || accept('\r') || accept('\n') || accept('\t');
    }

    private boolean acceptComment() {
        if (accept('/')) {
            if (accept('/')) {
                expectCommentSingle();
                return true;
            }

            if (accept('*')) {
                expectCommentMulti();
                return true;
            }

            error();
        }
        return false;
    }

    private void expectCommentSingle() {
        while (!match(EOF_CH) && !match(EOL_CH)) {
            consume();
        }
        expect(EOL_CH);
    }

    private void expectCommentMulti() {
        while (!match(EOF_CH)) {
            if (accept('*') && match('/')) {
                break;
            }
            consume();
        }
        expect('/');
    }

}
