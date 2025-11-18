package analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.TokenValue;

public class BuiltinArityChecker {
    private static final Map<String, Integer> BUILTIN_ARITIES = new HashMap<>();
    
    static {
        BUILTIN_ARITIES.put("plus", 2);
        BUILTIN_ARITIES.put("minus", 2);
        BUILTIN_ARITIES.put("times", 2);
        BUILTIN_ARITIES.put("divide", 2);
        
        BUILTIN_ARITIES.put("head", 1);
        BUILTIN_ARITIES.put("tail", 1);
        BUILTIN_ARITIES.put("cons", 2);
        
        BUILTIN_ARITIES.put("equal", 2);
        BUILTIN_ARITIES.put("nonequal", 2);
        BUILTIN_ARITIES.put("less", 2);
        BUILTIN_ARITIES.put("lesseq", 2);
        BUILTIN_ARITIES.put("greater", 2);
        BUILTIN_ARITIES.put("greatereq", 2);
        
        BUILTIN_ARITIES.put("isint", 1);
        BUILTIN_ARITIES.put("isreal", 1);
        BUILTIN_ARITIES.put("isbool", 1);
        BUILTIN_ARITIES.put("isnull", 1);
        BUILTIN_ARITIES.put("isatom", 1);
        BUILTIN_ARITIES.put("islist", 1);
        
        BUILTIN_ARITIES.put("and", 2);
        BUILTIN_ARITIES.put("or", 2);
        BUILTIN_ARITIES.put("xor", 2);
        BUILTIN_ARITIES.put("not", 1);
        
        BUILTIN_ARITIES.put("eval", 1);
        BUILTIN_ARITIES.put("print", 1);
    }
    
    public void check(Object ast) throws SemanticException {
        if (ast instanceof List) {
            List<?> list = (List<?>) ast;
            for (Object element : list) {
                checkRecursive(element);
            }
        }
    }
    
    private void checkRecursive(Object element) throws SemanticException {
        if (!(element instanceof List)) {
            return;
        }

        List<?> list = (List<?>) element;
        if (list.isEmpty()) {
            return;
        }

        Object first = list.get(0);
        if (first instanceof TokenValue) {
            TokenValue tv = (TokenValue) first;
            String opName = getOperatorName(tv); // уже есть в классе

            // ВАЖНО: не проверяем ничего внутри (quote ...)
            if ("quote".equals(opName)) {
                return;
            }

            // Проверка арности встроенных функций только вне quote
            if (BUILTIN_ARITIES.containsKey(opName)) {
                int expected = BUILTIN_ARITIES.get(opName);
                int actual = list.size() - 1;
                if (expected != actual) {
                    throw new SemanticException(
                        String.format("Built-in function '%s' expects %d arguments but got %d",
                                    opName, expected, actual));
                }
            }
        }

        // Рекурсивно проверяем аргументы
        for (int i = 1; i < list.size(); i++) {
            checkRecursive(list.get(i));
        }
    }
    
    private String getOperatorName(TokenValue tv) {
        if (tv.value instanceof String) {
            return ((String) tv.value).toLowerCase();
        }
        return tv.type.toLowerCase();
    }
}