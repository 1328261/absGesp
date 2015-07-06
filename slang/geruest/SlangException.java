public class SlangException extends Exception {
    private SourcePosition position;

    public SlangException(SourcePosition position, String message) {
        super(message);
        this.position = position;
    }

    public SlangException(String message) {
        this(null, message);
    }

    public SourcePosition getPosition() {
        return position;
    }

}
