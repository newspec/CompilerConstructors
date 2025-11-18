package analyzer;

import java.util.ArrayList;
import java.util.List;
import parser.TokenValue;

public class DeadCodeEliminator {
    public Object eliminate(Object ast) {
        return eliminateElement(ast, false);
    }
    
    private Object eliminateElement(Object element, boolean inCond) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            List<Object> result = new ArrayList<>();
            
            // Check if this is a cond
            boolean isCond = false;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue first = (TokenValue) list.get(0);
                if ("COND".equals(first.type)) {
                    isCond = true;
                }
            }
            
            boolean foundReturn = false;
            for (int i = 0; i < list.size(); i++) {
                Object child = list.get(i);
                
                // Если это cond, обрабатываем специально
                if (isCond && i == 0) {
                    result.add(child);
                    continue;
                }
                
                // Если мы в обычном списке (не cond) и нашли return, пропускаем остальное
                if (!isCond && foundReturn) {
                    continue;
                }
                
                // Обрабатываем элемент рекурсивно (для cond передаём true)
                Object processed = eliminateElement(child, isCond);
                result.add(processed);
                
                // Проверяем return ТОЛЬКО если это не внутри cond
                if (!isCond && processed instanceof List) {
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