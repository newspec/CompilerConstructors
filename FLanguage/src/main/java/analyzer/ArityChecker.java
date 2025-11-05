package analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.TokenValue;

public class ArityChecker {
    // Map function names to expected parameter counts
    private Map<String, Integer> functionArities = new HashMap<>();
    
    public void collectFunctions(Object ast) {
        // First pass: collect all function definitions
        collectFunctionsRecursive(ast);
    }
    
    private void collectFunctionsRecursive(Object element) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue first = (TokenValue) list.get(0);
                
                if ("FUNC".equals(first.type)) {
                    // (func name (params...) body)
                    TokenValue name = (TokenValue) list.get(1);
                    List<?> params = (List<?>) list.get(2);
                    functionArities.put((String) name.value, params.size());
                }
            }
            
            // Recurse through all elements
            for (Object child : list) {
                collectFunctionsRecursive(child);
            }
        }
    }
    
    public void checkCalls(Object ast) throws SemanticException {
        // Second pass: verify all function calls
        checkCallsRecursive(ast);
    }
    
    private void checkCallsRecursive(Object element) throws SemanticException {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue first = (TokenValue) list.get(0);
                
                // Check if it's a generic call to user-defined function
                if ("IDENTIFIER".equals(first.type)) {
                    String funcName = (String) first.value;
                    if (functionArities.containsKey(funcName)) {
                        int expected = functionArities.get(funcName);
                        int actual = list.size() - 1; // exclude function name
                        if (expected != actual) {
                            throw new SemanticException(
                                String.format("Function '%s' expects %d arguments but got %d",
                                    funcName, expected, actual));
                        }
                    }
                }
            }
            
            // Recurse
            for (Object child : list) {
                checkCallsRecursive(child);
            }
        }
    }
}