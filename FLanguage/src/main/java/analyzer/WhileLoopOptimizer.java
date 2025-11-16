package analyzer;

import java.util.ArrayList;
import java.util.List;
import parser.TokenValue;

/**
 * Удаляет dead while loops (циклы, которые никогда не выполняются)
 * и упрощает условные выражения с константными условиями
 */
public class WhileLoopOptimizer {
    
    public Object optimize(Object ast) {
        return optimizeElement(ast);
    }
    
    private Object optimizeElement(Object element) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return list;
            
            List<Object> optimized = new ArrayList<>();
            
            for (Object child : list) {
                Object optimizedChild = optimizeElement(child);
                
                // Проверяем, является ли это while loop с constant false условием
                if (shouldRemoveWhile(optimizedChild)) {
                    // Пропускаем этот while loop - он никогда не выполнится
                    continue;
                }
                
                optimized.add(optimizedChild);
            }
            
            return optimized;
        }
        return element;
    }
    
    /**
     * Проверяет, является ли элемент while loop с условием false
     */
    private boolean shouldRemoveWhile(Object element) {
        if (!(element instanceof List)) return false;
        
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return false;
        
        String opName = getOperatorName(list.get(0));
        
        // while форма: (while condition body)
        if ("while".equals(opName) && list.size() >= 2) {
            Object condition = list.get(1);
            
            // Если условие - это constant false
            if (isConstantFalse(condition)) {
                return true;  // Удаляем этот while
            }
        }
        
        return false;
    }
    
    /**
     * Проверяет, является ли значение constant false
     */
    private boolean isConstantFalse(Object obj) {
        if (obj instanceof TokenValue) {
            TokenValue tv = (TokenValue) obj;
            if ("BOOL".equals(tv.type)) {
                return tv.value.equals(false);
            }
        }
        return false;
    }
    
    /**
     * Получает название оператора/функции из первого элемента
     */
    private String getOperatorName(Object first) {
        if (first instanceof TokenValue) {
            TokenValue tv = (TokenValue) first;
            if (tv.value instanceof String) {
                return ((String) tv.value).toLowerCase();
            }
            return tv.type.toLowerCase();
        }
        return "";
    }
}