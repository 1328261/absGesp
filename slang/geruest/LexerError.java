public class LexerError extends Error {
    private SourcePosition position;

    public LexerError(SourcePosition position, String message) {
        super(message);
        this.position = position;
    }

    public LexerError(String message) {
        this(null, message);
    }

    public SourcePosition getPosition() {
        return position;
    }
}
