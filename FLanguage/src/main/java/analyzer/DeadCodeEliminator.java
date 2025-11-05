package analyzer;

import java.util.ArrayList;
import java.util.List;

import parser.TokenValue;

public class DeadCodeEliminator {
    public Object eliminate(Object ast) {
        return eliminateElement(ast);
    }
    
    private Object eliminateElement(Object element) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            List<Object> result = new ArrayList<>();
            
            boolean foundReturn = false;
            for (Object child : list) {
                if (foundReturn) {
                    // Skip all elements after return
                    continue;
                }
                
                Object processed = eliminateElement(child);
                result.add(processed);
                
                // Check if this was a return statement
                if (processed instanceof List) {
                    List<?> childList = (List<?>) processed;
                    if (!childList.isEmpty() && childList.get(0) instanceof TokenValue) {
                        TokenValue first = (TokenValue) childList.get(0);
                        if ("RETURN".equals(first.type)) {
                            foundReturn = true;
                        }
                    }
                }
            }
            
            return result;
        }
        return element;
    }
}
