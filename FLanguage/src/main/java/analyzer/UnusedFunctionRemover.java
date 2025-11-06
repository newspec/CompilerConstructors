package analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import parser.TokenValue;

/**
 * Removes unused function definitions.
 *
 * Important behavior:
 * - Function names are stored and compared in their original form (case-sensitive).
 * - Recognition of special forms (func, lambda, quote, ...) is done case-insensitively.
 */
public class UnusedFunctionRemover {

    public Object remove(Object ast) {
        if (!(ast instanceof List<?>)) return ast;

        List<?> program = (List<?>) ast;

        // First pass: collect all function definitions (store names as-is, case-sensitive)
        Set<String> definedFunctions = new HashSet<>();
        Set<String> calledFunctions = new HashSet<>();

        for (Object element : program) {
            if (isFunctionDefinition(element)) {
                String funcName = extractFunctionName(element);
                if (funcName != null) {
                    definedFunctions.add(funcName); // keep original case
                }
            }
        }

        // Second pass: collect all called functions (visit everything, including func defs)
        for (Object element : program) {
            collectCalledFunctions(element, calledFunctions, definedFunctions);
        }

        // Third pass: keep only functions that are called (or keep non-func elements)
        List<Object> optimized = new ArrayList<>();
        for (Object element : program) {
            if (isFunctionDefinition(element)) {
                String funcName = extractFunctionName(element);
                if (funcName != null) {
                    if (calledFunctions.contains(funcName)) {
                        optimized.add(element); // function is used somewhere (exact match)
                    } else {
                        // skip unused function (i.e. remove it)
                    }
                } else {
                    // If can't extract name, be conservative and keep it
                    optimized.add(element);
                }
            } else {
                optimized.add(element);
            }
        }

        return optimized;
    }

    /**
     * Checks if an element is a function definition.
     * Uses case-insensitive check for the 'func' operator.
     */
    private boolean isFunctionDefinition(Object element) {
        if (!(element instanceof List<?>)) return false;
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return false;

        String opName = getOperatorName(list.get(0));
        return opName != null && opName.equalsIgnoreCase("func") && list.size() > 1;
    }

    /**
     * Extracts function name from a function definition (returns raw name or null).
     */
    private String extractFunctionName(Object element) {
        if (!(element instanceof List<?>)) return null;
        List<?> list = (List<?>) element;
        if (list.size() < 2) return null;

        String opName = getOperatorName(list.get(0));
        if (opName == null || !opName.equalsIgnoreCase("func")) return null;

        Object nameObj = list.get(1);
        if (nameObj instanceof TokenValue) {
            TokenValue tv = (TokenValue) nameObj;
            if (tv.value != null) return tv.value.toString(); // keep case as-is
            if (tv.type != null) return tv.type.toString();
        }
        return null;
    }

    /**
     * Recursively collects all function calls in the AST.
     *
     * - called: set where we add function names that are called (stored as original-case strings)
     * - defined: set of defined function names (stored as original-case strings)
     *
     * Matching a call against a defined function is done by exact string match
     * (case-sensitive), i.e. a call "Zero" matches a definition "Zero" but not "zero".
     */
    private void collectCalledFunctions(Object element, Set<String> called, Set<String> defined) {
        if (element instanceof List<?>) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return;

            Object first = list.get(0);
            String opName = getOperatorName(first); // raw operator name (original case if TokenValue.value exists)
            boolean firstIsToken = first instanceof TokenValue;

            // If the first element is a token and its exact name matches a defined function, it's a call
            if (firstIsToken && opName != null) {
                if (defined.contains(opName)) {
                    called.add(opName); // store call with the exact case found in AST
                }
            }

            // Recurse into bodies appropriately:
            // - If it's a func definition, inspect its body (index 3) too (so calls inside functions count)
            String opNameLower = opName == null ? "" : opName.toLowerCase();
            if ("func".equals(opNameLower)) {
                if (list.size() > 3) {
                    collectCalledFunctions(list.get(3), called, defined);
                }
                // continue to recurse other parts as well
            } else if ("lambda".equals(opNameLower)) {
                if (list.size() > 2) {
                    collectCalledFunctions(list.get(2), called, defined);
                }
                // continue
            } else if ("quote".equals(opNameLower)) {
                // do not recurse inside quote
                return;
            }

            // Recurse into children.
            // If first element is an operator token, children start at index 1; otherwise start at 0.
            int startIndex = firstIsToken ? 1 : 0;
            for (int i = startIndex; i < list.size(); i++) {
                collectCalledFunctions(list.get(i), called, defined);
            }
        }
    }

    /**
     * Gets operator name from first element.
     *
     * IMPORTANT: this returns the token value *as-is* (preserves case) when possible.
     * For control/keyword comparisons we use equalsIgnoreCase / toLowerCase() at call sites.
     * If first is a List, returns empty string.
     */
    private String getOperatorName(Object first) {
        if (first == null) return "";
        if (first instanceof TokenValue) {
            TokenValue tv = (TokenValue) first;
            if (tv.value != null) return tv.value.toString(); // preserve case
            if (tv.type != null) return tv.type.toString();
        }
        if (first instanceof List<?>) return "";
        return first.toString();
    }
}
