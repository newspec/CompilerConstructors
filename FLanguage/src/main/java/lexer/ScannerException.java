package lexer;

/**
 * Exception thrown when a lexical scanning error occurs
 */
public class ScannerException extends Exception {
    private int line;
    private int column;
    private String text;
    
    public ScannerException(String message) {
        super(message);
        this.line = -1;
        this.column = -1;
        this.text = null;
    }
    
    public ScannerException(String message, int line, int column, String text) {
        super(message);
        this.line = line;
        this.column = column;
        this.text = text;
    }
    
    public ScannerException(String message, Throwable cause) {
        super(message, cause);
        this.line = -1;
        this.column = -1;
        this.text = null;
    }
    
    public int getLine() {
        return line;
    }
    
    public int getColumn() {
        return column;
    }
    
    public String getText() {
        return text;
    }
    
    @Override
    public String toString() {
        if (line >= 0 && column >= 0) {
            return String.format("ScannerException at line %d, column %d: %s (text: '%s')",
                line, column, getMessage(), text);
        }
        return "ScannerException: " + getMessage();
    }
}
