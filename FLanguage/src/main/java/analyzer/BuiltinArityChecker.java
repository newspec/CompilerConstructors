package analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.TokenValue;

public class BuiltinArityChecker {
    private static final Map<String, Integer> BUILTIN_ARITIES = new HashMap<>();
    
    static {
        // Arithmetic functions
        BUILTIN_ARITIES.put("PLUS", 2);
        BUILTIN_ARITIES.put("MINUS", 2);
        BUILTIN_ARITIES.put("TIMES", 2);
        BUILTIN_ARITIES.put("DIVIDE", 2);
        
        // List operations
        BUILTIN_ARITIES.put("HEAD", 1);
        BUILTIN_ARITIES.put("TAIL", 1);
        BUILTIN_ARITIES.put("CONS", 2);
        
        // Comparisons
        BUILTIN_ARITIES.put("EQUAL", 2);
        BUILTIN_ARITIES.put("LESS", 2);
        // ... add all comparison operators
        
        // Predicates
        BUILTIN_ARITIES.put("ISINT", 1);
        BUILTIN_ARITIES.put("ISREAL", 1);
        // ... add all predicates
        
        // Logical operators
        BUILTIN_ARITIES.put("AND", 2);
        BUILTIN_ARITIES.put("OR", 2);
        BUILTIN_ARITIES.put("XOR", 2);
        BUILTIN_ARITIES.put("NOT", 1);
        
        BUILTIN_ARITIES.put("EVAL", 1);
    }
    
    public void check(Object ast) throws SemanticException {
        checkRecursive(ast);
    }
    
    private void checkRecursive(Object element) throws SemanticException {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue first = (TokenValue) list.get(0);
                
                if (BUILTIN_ARITIES.containsKey(first.type)) {
                    int expected = BUILTIN_ARITIES.get(first.type);
                    int actual = list.size() - 1;
                    if (expected != actual) {
                        throw new SemanticException(
                            String.format("Built-in function '%s' expects %d arguments but got %d",
                                first.type.toLowerCase(), expected, actual));
                    }
                }
            }
            
            for (Object child : list) {
                checkRecursive(child);
            }
        }
    }
}