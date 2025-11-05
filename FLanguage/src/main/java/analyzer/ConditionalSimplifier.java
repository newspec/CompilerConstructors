package analyzer;

import java.util.ArrayList;
import java.util.List;

import parser.TokenValue;

public class ConditionalSimplifier {
    public Object simplify(Object ast) {
        return simplifyElement(ast);
    }
    
    private Object simplifyElement(Object element) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return list;
            
            // First, simplify all children
            List<Object> simplified = new ArrayList<>();
            for (Object child : list) {
                simplified.add(simplifyElement(child));
            }
            
            Object first = simplified.get(0);
            if (first instanceof TokenValue) {
                TokenValue op = (TokenValue) first;
                
                if ("COND".equals(op.type)) {
                    // (cond condition thenBranch [elseBranch])
                    Object condition = simplified.get(1);
                    
                    if (condition instanceof TokenValue) {
                        TokenValue condVal = (TokenValue) condition;
                        if ("BOOL".equals(condVal.type)) {
                            boolean value = (Boolean) condVal.value;
                            if (value) {
                                // Condition is true, return then branch
                                return simplified.get(2);
                            } else {
                                // Condition is false, return else branch (or null)
                                if (simplified.size() > 3) {
                                    return simplified.get(3);
                                } else {
                                    return new TokenValue("NULL", null);
                                }
                            }
                        }
                    }
                }
            }
            
            return simplified;
        }
        return element;
    }
}
