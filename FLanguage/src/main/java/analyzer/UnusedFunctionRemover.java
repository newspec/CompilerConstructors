package analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import parser.TokenValue;

/**
 * Removes unused function definitions.
 *
 * Fixes:
 *  - Normalizes function names to lower-case for comparisons.
 *  - When collecting calls, visits all top-level elements (including func defs)
 *    so calls inside function bodies are discovered.
 */
public class UnusedFunctionRemover {

    public Object remove(Object ast) {
        if (!(ast instanceof List<?>)) return ast;

        List<?> program = (List<?>) ast;

        // First pass: collect all function definitions (normalized to lower-case)
        Set<String> definedFunctions = new HashSet<>();
        Set<String> calledFunctions = new HashSet<>();

        for (Object element : program) {
            if (isFunctionDefinition(element)) {
                String funcName = extractFunctionName(element);
                if (funcName != null) {
                    definedFunctions.add(funcName.toLowerCase());
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
                    String lname = funcName.toLowerCase();
                    if (calledFunctions.contains(lname)) {
                        optimized.add(element); // function is used somewhere
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
     * Checks if an element is a function definition
     */
    private boolean isFunctionDefinition(Object element) {
        if (!(element instanceof List<?>)) return false;
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return false;

        String opName = getOperatorName(list.get(0));
        return "func".equals(opName) && list.size() > 1;
    }

    /**
     * Extracts function name from a function definition (returns raw name or null).
     */
    private String extractFunctionName(Object element) {
        if (!(element instanceof List<?>)) return null;
        List<?> list = (List<?>) element;
        if (list.size() < 2) return null;

        String opName = getOperatorName(list.get(0));
        if (!"func".equals(opName)) return null;

        Object nameObj = list.get(1);
        if (nameObj instanceof TokenValue) {
            TokenValue tv = (TokenValue) nameObj;
            if (tv.value != null) return tv.value.toString();
            if (tv.type != null) return tv.type.toString();
        }
        return null;
    }

    /**
     * Recursively collects all function calls in the AST.
     * - called: set where we add lower-cased function names that are called.
     * - defined: set of lower-cased defined function names (for recognition)
     */
    private void collectCalledFunctions(Object element, Set<String> called, Set<String> defined) {
        if (element instanceof List<?>) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return;

            Object first = list.get(0);
            String opName = getOperatorName(first);
            boolean firstIsToken = first instanceof TokenValue;

            // If the first element is a token and matches a defined function name, it's a call
            if (firstIsToken) {
                String opnameLower = opName != null ? opName.toLowerCase() : "";
                if (defined.contains(opnameLower)) {
                    called.add(opnameLower);
                }
            }

            // Recurse into bodies appropriately:
            // - If it's a func definition, inspect its body (index 3) too (so calls inside functions count)
            if ("func".equals(opName)) {
                if (list.size() > 3) {
                    collectCalledFunctions(list.get(3), called, defined);
                }
                // don't return here — we may also want to process other parts if necessary
            } else if ("lambda".equals(opName)) {
                if (list.size() > 2) {
                    collectCalledFunctions(list.get(2), called, defined);
                }
                // continue recursion below
            } else if ("quote".equals(opName)) {
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
     * Gets operator name from first element (lowercasing occurs where used).
     */
    private String getOperatorName(Object first) {
        if (first == null) return "";
        if (first instanceof TokenValue) {
            TokenValue tv = (TokenValue) first;
            if (tv.value != null) return tv.value.toString().toLowerCase().trim();
            if (tv.type != null) return tv.type.toString().toLowerCase().trim();
        }
        if (first instanceof List<?>) return "";
        return first.toString().toLowerCase().trim();
    }
}
