package analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import parser.TokenValue;

/**
 * Enhanced UndefinedVariableChecker with proper scope handling
 *
 * Key improvements:
 * - Distinguishes between S-expressions (operator at [0]) and wrapper lists
 * - Proper handling of nested scopes (func, lambda, prog)
 * - Pre-collection of local definitions before usage checking
 * - Debug logging for diagnostics
 * - Proper shadowing: local variables override global ones
 */
public class UndefinedVariableChecker {
    private final Set<String> globalDefinedAtoms = new HashSet<>();
    private final Set<String> undefinedErrors = new HashSet<>();
    private final Set<String> builtinFunctions = new HashSet<>();

    private boolean DEBUG = false;

    public UndefinedVariableChecker() {
        initializeBuiltins();
    }

    public void setDebug(boolean value) {
        this.DEBUG = value;
    }

    private void log(String fmt, Object... args) {
        if (!DEBUG) return;
        System.err.println(String.format(fmt, args));
    }

    private void initializeBuiltins() {
        Collections.addAll(builtinFunctions,
            "plus", "minus", "times", "divide",
            "head", "tail", "cons",
            "equal", "nonequal", "less", "lesseq", "greater", "greatereq",
            "isint", "isreal", "isbool", "isnull", "isatom", "islist",
            "and", "or", "xor", "not", "eval",
            "while", "cond", "break", "return",
            "print", "quote", "setq", "func", "lambda", "prog",
            "list"
        );
    }

    public List<String> check(Object ast) {
        globalDefinedAtoms.clear();
        undefinedErrors.clear();

        // First pass: collect all global function definitions
        // Also collect top-level setq definitions (global variables)
        if (ast instanceof List<?>) {
            List<?> program = (List<?>) ast;
            log("Program has %d top-level elements", program.size());
            for (Object element : program) {
                collectGlobalDefinitions(element);
            }
            log("Global definitions: %s", globalDefinedAtoms);

            // Second pass: check all usages
            for (Object element : program) {
                log("\n--- Checking element: %s", previewNode(element));
                checkUsages(element, new HashSet<>());
            }
        }

        return new ArrayList<>(undefinedErrors);
    }

    /**
     * Collects both global functions AND global variables (top-level setq)
     */
    private void collectGlobalDefinitions(Object element) {
        if (!(element instanceof List<?>)) return;
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return;

        String opName = getOperatorName(list.get(0));
        log("collectGlobalDefinitions: op='%s'", opName);

        // Global func definition
        if ("func".equals(opName) && list.size() > 1) {
            Object nameObj = list.get(1);
            String fname = extractNameFromTokenOrNode(nameObj);
            if (fname != null) {
                globalDefinedAtoms.add(fname);
                log("  Added global func: %s", fname);
            }
        }
        
        // Global variable definition (top-level setq)
        else if ("setq".equals(opName) && list.size() > 1) {
            Object varObj = list.get(1);
            String varName = extractNameFromTokenOrNode(varObj);
            if (varName != null) {
                globalDefinedAtoms.add(varName);
                log("  Added global variable: %s", varName);
            }
        }
    }

    private void checkUsages(Object element, Set<String> localScope) {
        if (element instanceof List<?>) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return;

            Object first = list.get(0);
            String opName = getOperatorName(first);
            boolean firstIsToken = first instanceof TokenValue;

            log("  checkUsages: op='%s' firstIsToken=%b localScope=%s", 
                opName, firstIsToken, localScope);

            // quote: don't process contents
            if ("quote".equals(opName)) {
                return;
            }

            // func: (func name (params...) body)
            if ("func".equals(opName)) {
                Set<String> funcScope = new HashSet<>(localScope);
                if (list.size() > 2 && list.get(2) instanceof List<?>) {
                    for (Object p : (List<?>) list.get(2)) {
                        String pn = extractNameFromTokenOrNode(p);
                        if (pn != null) funcScope.add(pn);
                    }
                }
                if (list.size() > 3) checkUsages(list.get(3), funcScope);
                return;
            }

            // lambda: (lambda (params...) body)
            if ("lambda".equals(opName)) {
                Set<String> lambdaScope = new HashSet<>(localScope);
                if (list.size() > 1 && list.get(1) instanceof List<?>) {
                    for (Object p : (List<?>) list.get(1)) {
                        String pn = extractNameFromTokenOrNode(p);
                        if (pn != null) lambdaScope.add(pn);
                    }
                }
                if (list.size() > 2) checkUsages(list.get(2), lambdaScope);
                return;
            }

            // prog: (prog (local-vars...) body...)
            // prog creates its own local scope
            // Local variables SHADOW global ones
            if ("prog".equals(opName)) {
                Set<String> progScope = new HashSet<>(localScope);
                Set<String> shadowed = new HashSet<>();
                
                // Add declared local variables
                // These shadow (override) global variables
                if (list.size() > 1 && list.get(1) instanceof List<?>) {
                    for (Object v : (List<?>) list.get(1)) {
                        String vn = extractNameFromTokenOrNode(v);
                        if (vn != null) {
                            progScope.add(vn);
                            shadowed.add(vn);  // Track shadowed variables
                            log("    Shadowing: %s", vn);
                        }
                    }
                }
                
                // Pre-collect all setq definitions in prog body
                // This allows variables to be visible to each other
                int bodyStart = 2;
                for (int i = bodyStart; i < list.size(); i++) {
                    collectLocalDefinitions(list.get(i), progScope, shadowed);
                }
                
                // Then check all body elements with full scope
                for (int i = bodyStart; i < list.size(); i++) {
                    checkUsages(list.get(i), progScope);
                }
                return;
            }

            // setq: (setq atom value)
            if ("setq".equals(opName)) {
                if (list.size() > 2) {
                    checkUsages(list.get(2), localScope);
                }
                return;
            }

            // Default: check arguments
            // If first is a token (operator), start from 1; otherwise from 0
            int startIndex = firstIsToken ? 1 : 0;
            for (int i = startIndex; i < list.size(); i++) {
                checkUsages(list.get(i), localScope);
            }

        } else if (element instanceof TokenValue) {
            TokenValue tv = (TokenValue) element;
            if (isIdentifierToken(tv)) {
                String name = tv.value != null ? tv.value.toString() : tv.type;
                if (!isDefinedAnywhere(name, localScope)) {
                    undefinedErrors.add(name);
                    log("  UNDEFINED: %s", name);
                }
            }
        }
    }

    /**
     * Collects local definitions (setq) inside an element
     * shadowed set tracks which global variables are shadowed
     */
    private void collectLocalDefinitions(Object element, Set<String> scope, Set<String> shadowed) {
        if (!(element instanceof List<?>)) return;
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return;

        Object first = list.get(0);
        String opName = getOperatorName(first);
        boolean firstIsToken = first instanceof TokenValue;

        log("    collectLocalDefinitions: op='%s'", opName);

        // If this is a setq, add to scope
        if ("setq".equals(opName) && list.size() > 1) {
            Object atomObj = list.get(1);
            String atomName = extractNameFromTokenOrNode(atomObj);
            if (atomName != null) {
                scope.add(atomName);
                if (globalDefinedAtoms.contains(atomName)) {
                    shadowed.add(atomName);
                    log("      Added local (shadowing): %s", atomName);
                } else {
                    log("      Added local: %s", atomName);
                }
            }
        }

        // Don't descend into nested scope-creating forms
        if ("prog".equals(opName) || "func".equals(opName) || "lambda".equals(opName)) {
            return;
        }

        // Recurse into children
        int startIndex = firstIsToken ? 1 : 0;
        for (int i = startIndex; i < list.size(); i++) {
            collectLocalDefinitions(list.get(i), scope, shadowed);
        }
    }

    /**
     * Collects local definitions without tracking shadowing (for non-prog context)
     */
    private void collectLocalDefinitions(Object element, Set<String> scope) {
        collectLocalDefinitions(element, scope, new HashSet<>());
    }

    /**
     * Checks if a TokenValue represents an identifier
     */
    private boolean isIdentifierToken(TokenValue tv) {
        if (tv == null || tv.type == null) return false;
        String t = tv.type.toUpperCase().trim();
        return "IDENTIFIER".equals(t) || "ATOM".equals(t) || "SYMBOL".equals(t) || "NAME".equals(t);
    }

    /**
     * Checks if a name is defined anywhere (built-in, local, or global)
     */
    private boolean isDefinedAnywhere(String name, Set<String> localScope) {
        if (name == null) return false;
        return builtinFunctions.contains(name) || localScope.contains(name) || globalDefinedAtoms.contains(name);
    }

    /**
     * Gets operator name from first element
     */
    private String getOperatorName(Object first) {
        if (first == null) return "";
        if (first instanceof TokenValue) {
            TokenValue tv = (TokenValue) first;
            if (tv.value != null) return tv.value.toString().toLowerCase().trim();
            if (tv.type != null) return tv.type.toLowerCase().trim();
        }
        if (first instanceof List<?>) return ""; // wrapper list, no operator
        return first.toString().toLowerCase().trim();
    }

    /**
     * Extracts name from a token or list node
     */
    private String extractNameFromTokenOrNode(Object node) {
        if (node == null) return null;
        if (node instanceof TokenValue) {
            TokenValue tv = (TokenValue) node;
            if (tv.value != null) return tv.value.toString();
            if (tv.type != null) return tv.type;
        }
        if (node instanceof List<?>) {
            List<?> list = (List<?>) node;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue tv = (TokenValue) list.get(0);
                if (tv.value != null) return tv.value.toString();
                if (tv.type != null) return tv.type;
            }
        }
        return null;
    }

    /**
     * Returns a brief description of a node for debugging
     */
    private String previewNode(Object node) {
        if (node == null) return "null";
        if (node instanceof TokenValue) {
            TokenValue tv = (TokenValue) node;
            return String.format("Token(%s=%s)", tv.type, tv.value);
        }
        if (node instanceof List<?>) {
            List<?> l = (List<?>) node;
            if (l.isEmpty()) return "()";
            return String.format("List(op=%s,len=%d)", getOperatorName(l.get(0)), l.size());
        }
        return node.toString();
    }
}