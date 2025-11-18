package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lexer.FScanner;

/**
 * Обёртка для FParser с дополнительной функциональностью
 */
public class FParserWrapper {
    private FParser parser;
    private List<String> errors;
    private Object parseResult;
    private ErrorCapturingScanner errorCapturingScanner;
    
    public FParserWrapper(FScanner scanner) {
        // Оборачиваем сканер для захвата ошибок
        this.errorCapturingScanner = new ErrorCapturingScanner(scanner);
        this.parser = new FParser(errorCapturingScanner);
        this.errors = new ArrayList<>();
    }
    
    /**
     * Выполняет парсинг
     */
    public boolean parse() throws IOException {
        try {
            boolean result = parser.parse();
            
            // Собираем ошибки из сканера
            errors.addAll(errorCapturingScanner.getErrors());
            
            // Используем рефлексию для доступа к приватному полю parseResult
            try {
                java.lang.reflect.Field field = FParser.class.getDeclaredField("parseResult");
                field.setAccessible(true);
                this.parseResult = field.get(parser);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Fallback: если parseResult недоступен, используем null
                this.parseResult = null;
            }
            
            return result;
        } catch (Exception e) {
            errors.add("Parse error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Получить результат парсинга
     */
    public Object getParseResult() {
        return parseResult;
    }
    
    /**
     * Получить список ошибок
     */
    public List<String> getErrors() {
        return errors;
    }
    
    /**
     * Проверить, есть ли ошибки
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    /**
     * Добавить ошибку
     */
    public void addError(String error) {
        errors.add(error);
    }
    
    /**
     * Внутренний класс для захвата ошибок парсера
     */
    private static class ErrorCapturingScanner implements parser.FParser.Lexer {
        private final FScanner scanner;
        private final List<String> errors;
        
        public ErrorCapturingScanner(FScanner scanner) {
            this.scanner = scanner;
            this.errors = new ArrayList<>();
        }
        
        @Override
        public int yylex() throws IOException {
            return scanner.yylex();
        }
        
        @Override
        public Object getLVal() {
            return scanner.getLVal();
        }
        
        @Override
        public void yyerror(String s) {
            errors.add(s);
            scanner.yyerror(s);
        }
        
        public List<String> getErrors() {
            return errors;
        }
    }
}