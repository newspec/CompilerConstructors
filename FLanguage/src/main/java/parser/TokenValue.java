package parser;

public class TokenValue {
    public final String type;
    public final Object value;

    public TokenValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type + ": " + value;
    }
}