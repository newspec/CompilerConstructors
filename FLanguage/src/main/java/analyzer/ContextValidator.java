package analyzer;

import java.util.List;
import parser.TokenValue;

/**
 * Validates context-sensitive keywords:
 * - 'return' can only appear inside func or lambda (tracked by functionDepth)
 * - 'break' can only appear inside while loop (tracked by loopDepth)
 *
 * Fix: correctly handle lists that are "wrappers" (first element is a List)
 * so that we iterate their elements starting from 0, not always from 1.
 */
public class ContextValidator {
    private int functionDepth = 0;  // tracks func/lambda/prog nesting (used for return)
    private int loopDepth = 0;      // tracks while nesting (used for break)

    public void validate(Object ast) throws SemanticException {
        if (ast instanceof List) {
            List<?> list = (List<?>) ast;
            for (Object element : list) {
                validateElement(element);
            }
        }
    }

    private void validateElement(Object element) throws SemanticException {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return;

            Object first = list.get(0);
            boolean firstIsToken = first instanceof TokenValue;
            String opName = getOperatorName(first);

            switch (opName) {
                case "return":
                    // only valid when inside a function-like context
                    if (functionDepth == 0) {
                        throw new SemanticException("'return' outside function context");
                    }
                    if (list.size() > 1) {
                        validateElement(list.get(1)); // validate return expression
                    }
                    return;

                case "break":
                    // only valid when inside a loop
                    if (loopDepth == 0) {
                        throw new SemanticException("'break' outside loop context");
                    }
                    return;

                case "func":
                    // (func name (params...) body)
                    functionDepth++;
                    if (list.size() > 3) {
                        validateElement(list.get(3)); // validate body
                    }
                    functionDepth--;
                    return;

                case "lambda":
                    // (lambda (params...) body)
                    functionDepth++;
                    if (list.size() > 2) {
                        validateElement(list.get(2)); // validate body
                    }
                    functionDepth--;
                    return;

                case "prog":
                    // (prog (local-vars...) body-elements...)
                    // we treat prog as a function-like scope (so return inside prog inside func is allowed)
                    functionDepth++;
                    if (list.size() > 2) {
                        for (int i = 2; i < list.size(); i++) {
                            validateElement(list.get(i));
                        }
                    }
                    functionDepth--;
                    return;

                case "while":
                    loopDepth++;
                    if (list.size() > 1) {
                        validateElement(list.get(1)); // condition
                    }
                    if (list.size() > 2) {
                        validateElement(list.get(2)); // body
                    }
                    loopDepth--;
                    return;

                case "quote":
                    // don't validate inside quote
                    return;

                default:
                    // For any other list: if first element is a token (operator),
                    // skip it and validate arguments starting from 1;
                    // if first element is NOT a token (i.e. it's itself a list / wrapper),
                    // then this list is a sequence-wrapper and we must iterate from 0.
                    int startIndex = firstIsToken ? 1 : 0;
                    for (int i = startIndex; i < list.size(); i++) {
                        validateElement(list.get(i));
                    }
                    return;
            }
        }

        // If it's not a list, no further validation needed (literals, tokens handled elsewhere)
    }

    /**
     * Gets operator name from first element (robustly).
     */
    private String getOperatorName(Object first) {
        if (first instanceof TokenValue) {
            TokenValue tv = (TokenValue) first;
            if (tv.value instanceof String) {
                return ((String) tv.value).toLowerCase();
            }
            if (tv.type != null) {
                return tv.type.toLowerCase();
            }
        }
        // If first is a List or other, return empty string -> treated as "no operator"
        return "";
    }
}
