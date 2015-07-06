public class SourcePosition {
    private int line;
    private int column;

    public SourcePosition(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "-:" + line + "," + column;
    }
}
