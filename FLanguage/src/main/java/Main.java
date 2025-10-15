import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lexer.FScanner;
import parser.FParser;
import parser.TokenValue;

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

        boolean ok = parser.parse();
        if (!ok) {
            System.err.println("Parse failed");
            System.exit(1);
        }

        Object ast = parser.getParseResult();

        List<String> errors = new ArrayList<>();
        Validator.validateProgram(ast, errors);
        if (!errors.isEmpty()) {
            for (String e : errors) {
                System.err.println(e);
            }
            System.exit(1);
        }

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

    private static final class Validator {
        private static final Map<String, int[]> ARITY = new HashMap<>();
        static {
            // exact arity [min,max]
            ARITY.put("plus", new int[]{2,2});
            ARITY.put("minus", new int[]{2,2});
            ARITY.put("times", new int[]{2,2});
            ARITY.put("divide", new int[]{2,2});
            ARITY.put("and", new int[]{2,2});
            ARITY.put("or", new int[]{2,2});
            ARITY.put("xor", new int[]{2,2});
            ARITY.put("not", new int[]{1,1});
            ARITY.put("equal", new int[]{2,2});
            ARITY.put("nonequal", new int[]{2,2});
            ARITY.put("less", new int[]{2,2});
            ARITY.put("lesseq", new int[]{2,2});
            ARITY.put("greater", new int[]{2,2});
            ARITY.put("greatereq", new int[]{2,2});
            // list ops
            ARITY.put("head", new int[]{1,1});
            ARITY.put("tail", new int[]{1,1});
            ARITY.put("cons", new int[]{2,2});
            // predicates (unary)
            ARITY.put("isint", new int[]{1,1});
            ARITY.put("isreal", new int[]{1,1});
            ARITY.put("isbool", new int[]{1,1});
            ARITY.put("isnull", new int[]{1,1});
            ARITY.put("isatom", new int[]{1,1});
            ARITY.put("islist", new int[]{1,1});
            // eval
            ARITY.put("eval", new int[]{1,1});
        }

        static void validateProgram(Object ast, List<String> errors) {
            if (!(ast instanceof List)) {
                errors.add("Program is not a list");
                return;
            }
            for (Object form : (List<?>) ast) {
                validateForm(form, errors);
            }
        }

        private static void validateForm(Object node, List<String> errors) {
            if (node instanceof List) {
                List<?> list = (List<?>) node;
                if (list.isEmpty()) {
                    errors.add("Empty list '()' is not a valid form");
                    return;
                }
                Object head = list.get(0);
                if (head instanceof TokenValue) {
                    TokenValue tv = (TokenValue) head;
                    if ("FUNCTION".equals(tv.type)) {
                        String fname = String.valueOf(tv.value);
                        validateFunctionCall(fname, list.subList(1, list.size()), errors);
                    } else if ("KEYWORD".equals(tv.type)) {
                        // For now, only shallow checks for a few keywords
                        String kw = String.valueOf(tv.value);
                        validateKeywordForm(kw, list.subList(1, list.size()), errors);
                    }
                }
                // Recurse into children
                for (Object child : list) {
                    if (child instanceof List) validateForm(child, errors);
                }
            }
        }

        private static void validateFunctionCall(String name, List<?> args, List<String> errors) {
            int[] range = ARITY.get(name);
            if (range != null) {
                int n = args.size();
                if (n < range[0] || n > range[1]) {
                    errors.add("Arity error: " + name + " expects " + range[0] + (range[0]==range[1]?"":".."+range[1]) + ", got " + n);
                    return;
                }
            }

            // Basic literal type checks for common ops
            switch (name) {
                case "plus":
                case "minus":
                case "times":
                case "divide":
                case "less":
                case "lesseq":
                case "greater":
                case "greatereq":
                case "equal":
                case "nonequal":
                    if (args.size() >= 2) {
                        checkNumericLiteral(args.get(0), name, errors);
                        checkNumericLiteral(args.get(1), name, errors);
                    }
                    break;
                case "and":
                case "or":
                case "xor":
                    if (args.size() >= 2) {
                        checkBooleanLiteral(args.get(0), name, errors);
                        checkBooleanLiteral(args.get(1), name, errors);
                    }
                    break;
                case "not":
                    if (args.size() == 1) {
                        checkBooleanLiteral(args.get(0), name, errors);
                    }
                    break;
                default:
                    break;
            }
        }

        private static void validateKeywordForm(String kw, List<?> args, List<String> errors) {
            // Minimal shape checks which are purely syntactic
            if ("lambda".equals(kw)) {
                if (args.size() < 2) {
                    errors.add("lambda expects at least 2 forms: params list and body");
                } else if (!(args.get(0) instanceof List)) {
                    errors.add("lambda first argument must be a parameter list");
                }
            } else if ("cond".equals(kw)) {
                // cond ((p1 e1) (p2 e2) ...)
                for (Object a : args) {
                    if (!(a instanceof List)) {
                        errors.add("cond expects each clause to be a list");
                        continue;
                    }
                    List<?> clause = (List<?>) a;
                    if (clause.size() < 2) {
                        errors.add("cond clause must have at least predicate and expression");
                    }
                }
            }
        }

        private static void checkNumericLiteral(Object node, String ctx, List<String> errors) {
            if (node instanceof TokenValue) {
                TokenValue tv = (TokenValue) node;
                if (!"INT".equals(tv.type) && !"REAL".equals(tv.type)) {
                    errors.add("Type error in " + ctx + ": expected numeric literal, got " + tv.type);
                }
            } else if (node instanceof List) {
                // allow nested expressions; no static type info available
            } else {
                errors.add("Type error in " + ctx + ": unexpected node type");
            }
        }

        private static void checkBooleanLiteral(Object node, String ctx, List<String> errors) {
            if (node instanceof TokenValue) {
                TokenValue tv = (TokenValue) node;
                if (!"BOOL".equals(tv.type)) {
                    errors.add("Type error in " + ctx + ": expected boolean literal, got " + tv.type);
                }
            } else if (node instanceof List) {
                // allow nested expressions; no static type info available
            } else {
                errors.add("Type error in " + ctx + ": unexpected node type");
            }
        }
    }
}
