import analyzer.SemanticAnalyzer;
import analyzer.SemanticAnalyzer.AnalysisResult;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import lexer.FScanner;
import lexer.FScannerWrapper;
import lexer.ScannerException;
import parser.FParserWrapper;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java Main <filename> | -c \"(code)\"");
            System.exit(1);
        }

        Reader reader;
        String source;
        
        if (args[0].equals("-c")) {
            if (args.length < 2) {
                System.err.println("Error: missing code after -c");
                System.exit(1);
            }
            source = args[1];
            reader = new StringReader(source);
        } else {
            source = args[0];
            try {
                reader = new FileReader(source);
            } catch (java.io.FileNotFoundException e) {
                System.err.println("Error: File not found: " + source);
                System.exit(1);
                return;
            }
        }

        try {
            // Этап 1: Лексический анализ и парсинг
            System.out.println("=== LEXICAL ANALYSIS & PARSING ===");
            System.out.println("Input: " + source);

            FScannerWrapper scannerWrapper;
            try {
                scannerWrapper = new FScannerWrapper(reader);
            } catch (ScannerException e) {
                System.err.println("Scanner error: " + e.getMessage());
                System.exit(1);
                return;
            } catch (Exception e) {
                System.err.println("Lexical error: " + e.getMessage());
                System.exit(1);
                return;
            }
            
            // Get the underlying FScanner for FParserWrapper
            FScanner scanner = scannerWrapper.getScanner();
            FParserWrapper parser = new FParserWrapper(scanner);

            try {
                parser.parse();
            } catch (RuntimeException e) {
                String message = e.getMessage();
                if (message != null && message.contains("Scanner error") || message.contains("Illegal character")) {
                    System.err.println("Scanner error during parsing: " + message);
                } else {
                    System.err.println("Parse error: " + message);
                }
                System.exit(1);
                return;
            } catch (Exception e) {
                System.err.println("Parse error: " + e.getMessage());
                System.exit(1);
                return;
            }

            Object ast = parser.getParseResult();

            // ВАЖНО: Проверяем ошибки парсера ДО любой дальнейшей обработки
            if (parser.hasErrors()) {
                System.err.println("Parse errors:");
                for (String error : parser.getErrors()) {
                    System.err.println("  " + error);
                }
                System.exit(1);
                return;
            }

            System.out.println("\n=== ORIGINAL AST ===");
            printAST(ast, 0);

            // Этап 2: Семантический анализ
            System.out.println("\n=== SEMANTIC ANALYSIS ===");

            SemanticAnalyzer analyzer = new SemanticAnalyzer();
            AnalysisResult result;
            
            try {
                result = analyzer.analyze(ast);
            } catch (Exception e) {
                System.err.println("Semantic analysis error: " + e.getMessage());
                System.exit(1);
                return;
            }

            // Выводим предупреждения
            if (!result.warnings.isEmpty()) {
                System.out.println("Warnings:");
                for (String warning : result.warnings) {
                    System.out.println("    " + warning);
                }
            } else {
                System.out.println("No warnings");
            }

            // Проверяем на ошибки
            if (result.hasErrors()) {
                System.err.println("Semantic errors:");
                for (String error : result.errors) {
                    System.err.println("    " + error);
                }
                System.exit(1);
                return;
            }

            // Этап 3: Оптимизированный AST
            System.out.println("\n=== OPTIMIZED AST ===");
            printAST(result.optimizedAST, 0);

            // Этап 4: Интерпретация
            System.out.println("\n=== INTERPRETATION ===");

            Interpreter interpreter = new Interpreter();
            RuntimeException lastError = null;

            try {
                interpreter.interpretAll(result.optimizedAST);
            } catch (RuntimeException e) {
                // Сохраняем ошибку, но продолжаем
                lastError = e;
            }

            System.out.println("\n=== EXECUTION RESULTS ===");
            List<Object> results = interpreter.getAccumulatedResults();
            if (results != null && results.size() > 0) {
                for (Object r : results) {
                    System.out.println(formatValue(r));
                }
            }

            // Если была ошибка во время выполнения, выводим её
            if (lastError != null) {
                System.err.println("Runtime error: " + lastError.getMessage());
                System.exit(1);
                return;
            }

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Prints the AST in a hierarchical, human-readable format
     */
    private static void printAST(Object node, int indent) {
        String indentation = " ".repeat(indent);
        
        if (node instanceof List) {
            List<?> list = (List<?>) node;
            if (list.isEmpty()) {
                System.out.println(indentation + "()");
            } else {
                System.out.println(indentation + "(");
                for (Object child : list) {
                    printAST(child, indent + 2);
                }
                System.out.println(indentation + ")");
            }
        } else {
            System.out.println(indentation + formatNode(node));
        }
    }

    /**
     * Formats a single AST node for display
     */
    private static String formatNode(Object node) {
        if (node == null) {
            return "null";
        }
        
        if (node instanceof parser.TokenValue) {
            parser.TokenValue tv = (parser.TokenValue) node;
            if ("NULL".equals(tv.type)) {
                return "null";
            }
            if ("BOOL".equals(tv.type)) {
                return tv.value.toString();
            }
            if ("INT".equals(tv.type) || "REAL".equals(tv.type)) {
                return tv.value.toString();
            }
            if ("IDENTIFIER".equals(tv.type)) {
                return tv.value.toString();
            }
            return tv.value.toString();
        }
        
        return node.toString();
    }

    /**
     * Formats values returned from interpretation for display
     */
    private static String formatValue(Object obj) {
        // For BuiltinFunction objects
        if (obj instanceof Interpreter.BuiltinFunction) {
            return "<builtin:" + ((Interpreter.BuiltinFunction) obj).name + ">";
        }

        if (obj == null) {
            return "null";
        }
        
        if (obj instanceof Boolean) {
            return obj.toString();
        }
        
        if (obj instanceof Integer || obj instanceof Double) {
            return obj.toString();
        }
        
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (list.isEmpty()) {
                return "()";
            }
            
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) sb.append(" ");
                sb.append(formatValue(list.get(i)));
            }
            sb.append(")");
            return sb.toString();
        }
        
        // For TokenValue objects that might be returned
        if (obj instanceof parser.TokenValue) {
            parser.TokenValue tv = (parser.TokenValue) obj;
            return tv.value.toString();
        }
        
        // For user-defined functions
        if (obj instanceof Interpreter.UserFunction) {
            return "<function>";
        }
        
        return obj.toString();
    }
}
