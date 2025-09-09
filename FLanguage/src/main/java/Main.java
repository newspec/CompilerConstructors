import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import lexer.FScanner;
import parser.FParser;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java Main <filename> | -c \"(code)\"");
            return;
        }

        Reader reader;
        if (args[0].equals("-c")) {
            if (args.length < 2) {
                System.err.println("Error: missing code after -c");
                return;
            }
            reader = new StringReader(args[1]);
        } else {
            reader = new FileReader(args[0]);
        }

        FScanner scanner = new FScanner(reader);
        FParser parser = new FParser(scanner);

        parser.parse();

        Object ast = parser.getParseResult();

        printAST(ast, 0);
    }

    private static void printAST(Object node, int indent) {
        if (node instanceof List) {
            System.out.println(" ".repeat(indent) + "(");
            for (Object child : (List<?>) node) {
                printAST(child, indent + 2);
            }
            System.out.println(" ".repeat(indent) + ")");
        } else {
            System.out.println(" ".repeat(indent) + node);
        }
    }
}
