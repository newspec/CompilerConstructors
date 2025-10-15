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
            Env env = new Env();
            if (!(ast instanceof List)) {
                errors.add("Program is not a list");
                return;
            }
            for (Object form : (List<?>) ast) {
                validateTopLevelForm(env, form, errors);
            }
        }

        private static final class Env {
            final Map<String, Integer> funcArity = new HashMap<>();
            final List<List<String>> varScopes = new ArrayList<>();
            int inFunction = 0;
            int inWhile = 0;

            Env() { pushScope(); }
            void pushScope() { varScopes.add(new ArrayList<>()); }
            void popScope() { if (!varScopes.isEmpty()) varScopes.remove(varScopes.size()-1); }
            void declareVar(String name) { currentScope().add(name); }
            boolean isVarInScope(String name) { return currentScope().contains(name); }
            private List<String> currentScope() { return varScopes.get(varScopes.size()-1); }
        }

        private static void validateTopLevelForm(Env env, Object node, List<String> errors) {
            validateForm(env, node, errors);
        }

        private static void validateForm(Env env, Object node, List<String> errors) {
            if (!(node instanceof List)) return;
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
                    validateBuiltinFunctionCall(env, fname, list.subList(1, list.size()), errors);
                    // Recurse into args
                    for (Object arg : list.subList(1, list.size())) {
                        // Variable reference check for identifiers used as values
                        if (arg instanceof TokenValue && "IDENTIFIER".equals(((TokenValue) arg).type)) {
                            validateVariableRef(env, (TokenValue) arg, errors);
                        }
                        validateForm(env, arg, errors);
                    }
                    return;
                } else if ("KEYWORD".equals(tv.type)) {
                    String kw = String.valueOf(tv.value);
                    validateKeywordForm(env, kw, list.subList(1, list.size()), errors);
                    return;
                } else if ("IDENTIFIER".equals(tv.type)) {
                    String name = String.valueOf(tv.value);
                    Integer ar = env.funcArity.get(name);
                    if (ar != null) {
                        // Validate user-defined function call arity
                        int n = list.size()-1;
                        if (n != ar) {
                            errors.add("Arity error: call to '"+name+"' expects " + ar + ", got " + n);
                        }
                    } else {
                        errors.add("Unknown function: '" + name + "'");
                    }
                    // Recurse into args
                    for (Object arg : list.subList(1, list.size())) {
                        if (arg instanceof TokenValue && "IDENTIFIER".equals(((TokenValue) arg).type)) {
                            validateVariableRef(env, (TokenValue) arg, errors);
                        }
                        validateForm(env, arg, errors);
                    }
                    return;
                }
            }
            // Generic recursion
            for (Object child : list) {
                if (child instanceof TokenValue && "IDENTIFIER".equals(((TokenValue) child).type)) {
                    validateVariableRef(env, (TokenValue) child, errors);
                }
                validateForm(env, child, errors);
            }
        }

        private static void validateBuiltinFunctionCall(Env env, String name, List<?> args, List<String> errors) {
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
                    if (args.size() >= 2) {
                        checkNumericLiteral(args.get(0), name, errors);
                        checkNumericLiteral(args.get(1), name, errors);
                    }
                    break;
                case "equal":
                case "nonequal":
                    if (args.size() >= 2) {
                        checkComparableLiteral(args.get(0), name, errors);
                        checkComparableLiteral(args.get(1), name, errors);
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
                case "head":
                case "tail":
                    if (args.size() == 1) {
                        checkNonNullListLike(args.get(0), name, errors);
                    }
                    break;
                case "cons":
                    if (args.size() == 2) {
                        // second arg should be list-like when it's a literal
                        checkListLike(args.get(1), name, errors);
                    }
                    break;
                default:
                    break;
            }
        }

        private static void validateKeywordForm(Env env, String kw, List<?> args, List<String> errors) {
            switch (kw) {
                case "lambda": {
                    if (args.size() < 2) {
                        errors.add("lambda expects at least 2 forms: params list and body");
                        return;
                    }
                    Object params = args.get(0);
                    if (!(params instanceof List)) {
                        errors.add("lambda first argument must be a parameter list");
                        return;
                    }
                    List<?> plist = (List<?>) params;
                    List<String> seen = new ArrayList<>();
                    for (Object p : plist) {
                        if (!(p instanceof TokenValue) || !"IDENTIFIER".equals(((TokenValue) p).type)) {
                            errors.add("lambda parameters must be identifiers");
                            return;
                        }
                        String name = String.valueOf(((TokenValue) p).value);
                        if (seen.contains(name)) {
                            errors.add("lambda parameters must be unique; duplicated: " + name);
                            return;
                        }
                        seen.add(name);
                    }
                    // Validate body in new scope
                    env.inFunction++;
                    env.pushScope();
                    for (String v : seen) env.declareVar(v);
                    for (Object b : args.subList(1, args.size())) validateForm(env, b, errors);
                    env.popScope();
                    env.inFunction--;
                    break;
                }
                case "func": {
                    if (args.size() < 3) {
                        errors.add("func expects name, parameter list, and body");
                        return;
                    }
                    Object nameNode = args.get(0);
                    if (!(nameNode instanceof TokenValue) || !"IDENTIFIER".equals(((TokenValue) nameNode).type)) {
                        errors.add("func name must be an identifier");
                        return;
                    }
                    String fname = String.valueOf(((TokenValue) nameNode).value);
                    Object params = args.get(1);
                    if (!(params instanceof List)) {
                        errors.add("func second argument must be a parameter list");
                        return;
                    }
                    List<?> plist = (List<?>) params;
                    List<String> seen = new ArrayList<>();
                    for (Object p : plist) {
                        if (!(p instanceof TokenValue) || !"IDENTIFIER".equals(((TokenValue) p).type)) {
                            errors.add("func parameters must be identifiers");
                            return;
                        }
                        String name = String.valueOf(((TokenValue) p).value);
                        if (seen.contains(name)) {
                            errors.add("func parameters must be unique; duplicated: " + name);
                            return;
                        }
                        seen.add(name);
                    }
                    env.funcArity.put(fname, plist.size());
                    env.inFunction++;
                    env.pushScope();
                    env.declareVar(fname); // name is available in its body (optional)
                    for (String v : seen) env.declareVar(v);
                    for (Object b : args.subList(2, args.size())) validateForm(env, b, errors);
                    env.popScope();
                    env.inFunction--;
                    break;
                }
                case "setq": {
                    if (args.size() != 2) {
                        errors.add("setq expects 2 arguments: identifier and value");
                        return;
                    }
                    Object id = args.get(0);
                    if (!(id instanceof TokenValue) || !"IDENTIFIER".equals(((TokenValue) id).type)) {
                        errors.add("setq first argument must be an identifier");
                        return;
                    }
                    String varName = String.valueOf(((TokenValue) id).value);
                    env.declareVar(varName);
                    // If assigning lambda, capture arity
                    Object val = args.get(1);
                    Integer lamAr = extractLambdaArity(val);
                    if (lamAr != null) env.funcArity.put(varName, lamAr);
                    validateForm(env, val, errors);
                    break;
                }
                case "prog": {
                    if (args.isEmpty()) {
                        errors.add("prog expects a declarations list and body");
                        return;
                    }
                    Object decls = args.get(0);
                    if (!(decls instanceof List)) {
                        errors.add("prog first argument must be a list (declarations)");
                    }
                    env.pushScope();
                    for (Object b : args.subList(1, args.size())) validateForm(env, b, errors);
                    env.popScope();
                    break;
                }
                case "while": {
                    if (args.size() < 2) {
                        errors.add("while expects a predicate and at least one body form");
                        return;
                    }
                    // validate predicate and body in a while context
                    checkPredicate(env, args.get(0), errors, "while predicate must be boolean");
                    validateForm(env, args.get(0), errors);
                    env.inWhile++;
                    for (Object b : args.subList(1, args.size())) validateForm(env, b, errors);
                    env.inWhile--;
                    break;
                }
                case "return": {
                    if (args.size() != 1) {
                        errors.add("return expects exactly one argument");
                        return;
                    }
                    if (env.inFunction == 0) {
                        errors.add("return used outside of function or lambda");
                    }
                    validateForm(env, args.get(0), errors);
                    break;
                }
                case "break": {
                    if (!args.isEmpty()) {
                        errors.add("break expects no arguments");
                    }
                    if (env.inWhile == 0) {
                        errors.add("break used outside of while");
                    }
                    break;
                }
                case "cond": {
                    for (Object a : args) {
                        if (!(a instanceof List)) {
                            errors.add("cond expects each clause to be a list");
                            continue;
                        }
                        List<?> clause = (List<?>) a;
                        if (clause.size() < 2) {
                            errors.add("cond clause must have at least predicate and expression");
                            continue;
                        }
                        // validate predicate and expression(s)
                        checkPredicate(env, clause.get(0), errors, "cond predicate must be boolean");
                        validateForm(env, clause.get(0), errors);
                        for (Object c : clause.subList(1, clause.size())) validateForm(env, c, errors);
                    }
                    break;
                }
                case "quote": {
                    if (args.size() != 1) {
                        errors.add("quote expects exactly one argument");
                    }
                    // do not validate inside quoted data
                    break;
                }
                default: {
                    // Unknown keyword: nothing to do
                    break;
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

        private static void checkListLike(Object node, String ctx, List<String> errors) {
            if (node instanceof List) return; // list expression
            if (node instanceof TokenValue) {
                TokenValue tv = (TokenValue) node;
                // NULL literal is allowed as empty list
                if ("NULL".equals(tv.type)) return;
                // Identifiers may evaluate to lists at runtime; allow
                if ("IDENTIFIER".equals(tv.type)) return;
                errors.add("Type error in " + ctx + ": expected list (or null), got " + tv.type);
                return;
            }
            errors.add("Type error in " + ctx + ": unexpected node type");
        }

        private static void checkNonNullListLike(Object node, String ctx, List<String> errors) {
            if (node instanceof List) return;
            if (node instanceof TokenValue) {
                TokenValue tv = (TokenValue) node;
                if ("NULL".equals(tv.type)) {
                    errors.add("Type error in " + ctx + ": empty list (null) is not allowed");
                    return;
                }
                if ("IDENTIFIER".equals(tv.type)) return; // may be a list at runtime
                errors.add("Type error in " + ctx + ": expected non-empty list, got " + tv.type);
                return;
            }
            errors.add("Type error in " + ctx + ": unexpected node type");
        }

        private static void checkComparableLiteral(Object node, String ctx, List<String> errors) {
            if (node instanceof TokenValue) {
                TokenValue tv = (TokenValue) node;
                if (!"INT".equals(tv.type) && !"REAL".equals(tv.type) && !"BOOL".equals(tv.type)) {
                    errors.add("Type error in " + ctx + ": expected numeric or boolean literal, got " + tv.type);
                }
            } else if (node instanceof List) {
                // allow nested expressions
            } else {
                errors.add("Type error in " + ctx + ": unexpected node type");
            }
        }

        private static void validateVariableRef(Env env, TokenValue tv, List<String> errors) {
            String name = String.valueOf(tv.value);
            // If identifier is used in functional position, это проверяется отдельно.
            // Здесь минимальная проверка: переменная может быть объявлена setq/параметрами/func-именем.
            boolean declared = false;
            for (int i = env.varScopes.size()-1; i >= 0 && !declared; i--) {
                if (env.varScopes.get(i).contains(name)) declared = true;
            }
            if (!declared) {
                errors.add("Undeclared identifier: '" + name + "'");
            }
        }

        private static void checkPredicate(Env env, Object node, List<String> errors, String message) {
            if (node instanceof TokenValue) {
                TokenValue tv = (TokenValue) node;
                if (!"BOOL".equals(tv.type)) {
                    // permit non-literals but flag non-boolean literals
                    if (!"IDENTIFIER".equals(tv.type)) {
                        errors.add(message + ": got " + tv.type);
                    }
                }
            }
            // For lists or identifiers, we cannot statically type—allow.
        }

        private static Integer extractLambdaArity(Object node) {
            if (!(node instanceof List)) return null;
            List<?> list = (List<?>) node;
            if (list.isEmpty()) return null;
            Object head = list.get(0);
            if (!(head instanceof TokenValue)) return null;
            TokenValue tv = (TokenValue) head;
            if (!"KEYWORD".equals(tv.type) || !"lambda".equals(String.valueOf(tv.value))) return null;
            if (list.size() < 2) return null;
            Object params = list.get(1);
            if (!(params instanceof List)) return null;
            return ((List<?>) params).size();
        }
    }
}
