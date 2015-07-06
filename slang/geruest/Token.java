public class Token {

    enum Type {
        EOF,
        INT,
        ATOM,
        STR,
    }

    public Type type;
    public String data;
    public SourcePosition position;

    public Token(Type type, SourcePosition position) {
        this.type = type;
        this.position = position;
    }

    public Token(Type type, String data, SourcePosition position) {
        this.type = type;
        this.data = data;
        this.position = position;
    }

    public String toString() {
        if (type != Type.INT && type != Type.ATOM && type != Type.STR) // @@ yuck
            return String.format("<%s>", type);
        return String.format("<%s, \"%s\">", type, data);
    }

}
