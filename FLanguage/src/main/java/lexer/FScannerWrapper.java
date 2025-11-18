package lexer;

import java.io.IOException;

/**
 * Wrapper around FScanner that catches exceptions and converts them to ScannerException
 */
public class FScannerWrapper implements parser.FParser.Lexer {
    
    private final FScanner scanner;
    private int line = 1;
    private int column = 1;
    
    public FScannerWrapper(java.io.Reader reader) throws ScannerException {
        try {
            this.scanner = new FScanner(reader);
        } catch (RuntimeException e) {
            throw new ScannerException("Failed to initialize scanner: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ScannerException("Failed to initialize scanner: " + e.getMessage(), e);
        }
    }
    
    @Override
    public int yylex() throws IOException {
        try {
            int token = scanner.yylex();
            
            // Update line and column tracking
            String text = scanner.yytext();
            for (char c : text.toCharArray()) {
                if (c == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
            }
            
            return token;
        } catch (RuntimeException e) {
            // FScanner throws RuntimeException for illegal characters
            String message = e.getMessage();
            if (message != null && message.contains("Illegal character")) {
                throw new RuntimeException("Illegal character at line " + line + 
                    ", column " + column + ": " + message);
            }
            throw new RuntimeException("Scanner error: " + message);
        } catch (Exception e) {
            throw new RuntimeException("Scanner error: " + e.getMessage());
        }
    }
    
    @Override
    public Object getLVal() {
        return scanner.getLVal();
    }
    
    @Override
    public void yyerror(String s) {
        throw new RuntimeException("Scanner error: " + s);
    }
    
    public void yyclose() throws IOException {
        scanner.yyclose();
    }
    
    public String yytext() {
        return scanner.yytext();
    }
    
    /**
     * Returns the underlying FScanner instance
     */
    public FScanner getScanner() {
        return scanner;
    }
}
