import analyzer.SemanticAnalyzer;
import analyzer.SemanticAnalyzer.AnalysisResult;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import lexer.FScanner;
import parser.FParserWrapper;


public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java Main <filename> | -c \"(code)\"");
            return;
        }

        Reader reader;
        String source;
        
        if (args[0].equals("-c")) {
            if (args.length < 2) {
                System.err.println("Error: missing code after -c");
                return;
            }
            source = args[1];
            reader = new StringReader(source);
        } else {
            source = args[0];
            reader = new FileReader(source);
        }

        try {
            // Этап 1: Лексический анализ и парсинг
            System.out.println("=== LEXICAL ANALYSIS & PARSING ===");
            System.out.println("Input: " + source);

            FScanner scanner = new FScanner(reader);
            FParserWrapper parser = new FParserWrapper(scanner);

            parser.parse();
            Object ast = parser.getParseResult();

            if (parser.hasErrors()) {
                System.err.println("Parse errors:");
                for (String error : parser.getErrors()) {
                    System.err.println("  - " + error);
                }
                return;
            }

            System.out.println("\n=== ORIGINAL AST ===");
            printAST(ast, 0);

            // Этап 2: Семантический анализ
            System.out.println("\n=== SEMANTIC ANALYSIS ===");

            SemanticAnalyzer analyzer = new SemanticAnalyzer();
            AnalysisResult result = analyzer.analyze(ast);

            // Выводим предупреждения
            if (!result.warnings.isEmpty()) {
                System.out.println("Warnings:");
                for (String warning : result.warnings) {
                    System.out.println("  ⚠ " + warning);
                }
            } else {
                System.out.println("No warnings");
            }

            // Проверяем на ошибки
            if (result.hasErrors()) {
                System.err.println("Semantic errors:");
                for (String error : result.errors) {
                    System.err.println("  ✗ " + error);
                }
                System.exit(1);
            }

            // Этап 3: Оптимизированный AST
            System.out.println("\n=== OPTIMIZED AST ===");
            printAST(result.optimizedAST, 0);

            System.out.println("\n✓ Compilation successful");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

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
            return tv.value.toString();
        }
        
        return node.toString();
    }
}