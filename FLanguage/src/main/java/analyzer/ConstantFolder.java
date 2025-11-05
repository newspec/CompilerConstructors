package analyzer;

import java.util.ArrayList;
import java.util.List;
import parser.TokenValue;

public class ConstantFolder {
    public Object fold(Object ast) {
        return foldElement(ast);
    }
    
    private Object foldElement(Object element) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return list;
            
            Object first = list.get(0);
            if (!(first instanceof TokenValue)) {
                // Если это wrapper list, обрабатываем как обычно
                List<Object> folded = new ArrayList<>();
                for (Object child : list) {
                    folded.add(foldElement(child));
                }
                return folded;
            }
            
            String opName = getOperatorName(first);
            
            // ВАЖНО: quote предотвращает оптимизацию содержимого!
            if ("quote".equals(opName)) {
                // Не оптимизируем внутренность quote
                // Просто возвращаем как есть
                return list;
            }
            
            // First, fold all children (except for quote)
            List<Object> folded = new ArrayList<>();
            for (Object child : list) {
                folded.add(foldElement(child));
            }
            
            // Try to fold comparison operations with 2 arguments
            if (folded.size() == 3) {
                Object arg1 = folded.get(1);
                Object arg2 = folded.get(2);
                
                if (isConstantValue(arg1) && isConstantValue(arg2)) {
                    switch (opName) {
                        case "plus":
                            return foldArithmetic(arg1, arg2, (a, b) -> a + b);
                        case "minus":
                            return foldArithmetic(arg1, arg2, (a, b) -> a - b);
                        case "times":
                            return foldArithmetic(arg1, arg2, (a, b) -> a * b);
                        case "divide":
                            return foldArithmetic(arg1, arg2, (a, b) -> {
                                if (Math.abs(b) < 1e-10) return null;
                                return a / b;
                            });
                        case "less":
                            return new TokenValue("BOOL", 
                                getLiteralValue(arg1) < getLiteralValue(arg2));
                        case "lesseq":
                            return new TokenValue("BOOL", 
                                getLiteralValue(arg1) <= getLiteralValue(arg2));
                        case "greater":
                            return new TokenValue("BOOL", 
                                getLiteralValue(arg1) > getLiteralValue(arg2));
                        case "greatereq":
                            return new TokenValue("BOOL", 
                                getLiteralValue(arg1) >= getLiteralValue(arg2));
                        case "equal":
                            boolean result = compareValues(arg1, arg2);
                            return new TokenValue("BOOL", result);
                        case "nonequal":
                            return new TokenValue("BOOL", 
                                !compareValues(arg1, arg2));
                        case "and":
                            boolean val1 = getBoolValue(arg1);
                            boolean val2 = getBoolValue(arg2);
                            return new TokenValue("BOOL", val1 && val2);
                        case "or":
                            val1 = getBoolValue(arg1);
                            val2 = getBoolValue(arg2);
                            return new TokenValue("BOOL", val1 || val2);
                        case "xor":
                            val1 = getBoolValue(arg1);
                            val2 = getBoolValue(arg2);
                            return new TokenValue("BOOL", val1 != val2);
                    }
                }
            }
            
            // Single argument operations
            if (folded.size() == 2 && isConstantValue(folded.get(1))) {
                Object arg = folded.get(1);
                switch (opName) {
                    case "not":
                        boolean val = getBoolValue(arg);
                        return new TokenValue("BOOL", !val);
                    case "isint":
                        return new TokenValue("BOOL", isInt(arg));
                    case "isreal":
                        return new TokenValue("BOOL", isReal(arg));
                    case "isbool":
                        return new TokenValue("BOOL", isBool(arg));
                    case "isnull":
                        return new TokenValue("BOOL", isNull(arg));
                    case "isatom":
                        return new TokenValue("BOOL", isAtom(arg));
                    case "islist":
                        return new TokenValue("BOOL", arg instanceof List);
                }
            }
            
            return folded;
        }
        return element;
    }
    
    /**
     * Проверяет, является ли значение константой (литерал или quoted значение)
     */
    private boolean isConstantValue(Object obj) {
        if (isLiteral(obj)) return true;
        
        // Quoted значение - тоже константа
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                String opName = getOperatorName(list.get(0));
                if ("quote".equals(opName)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Сравнивает два значения (включая quoted atoms и lists)
     * '5 эквивалентно 5
     * 'x эквивалентно x
     * '(1 2) эквивалентно (1 2)
     */
    private boolean compareValues(Object arg1, Object arg2) {
        // Развёртываем quoted значения
        Object unquoted1 = unquote(arg1);
        Object unquoted2 = unquote(arg2);
        
        // Оба развёрнутые литералы (INT, REAL, BOOL, NULL)
        if (isLiteral(unquoted1) && isLiteral(unquoted2)) {
            TokenValue tv1 = (TokenValue) unquoted1;
            TokenValue tv2 = (TokenValue) unquoted2;
            
            if ("NULL".equals(tv1.type) && "NULL".equals(tv2.type)) {
                return true;
            }
            
            if ("INT".equals(tv1.type) && "INT".equals(tv2.type)) {
                return tv1.value.equals(tv2.value);
            }
            
            if ("REAL".equals(tv1.type) && "REAL".equals(tv2.type)) {
                return Math.abs((Double) tv1.value - (Double) tv2.value) < 1e-10;
            }
            
            if ("BOOL".equals(tv1.type) && "BOOL".equals(tv2.type)) {
                return tv1.value.equals(tv2.value);
            }
            
            return false;
        }
        
        // Оба развёрнутые атомы (IDENTIFIER)
        if (isAtomToken(unquoted1) && isAtomToken(unquoted2)) {
            TokenValue tv1 = (TokenValue) unquoted1;
            TokenValue tv2 = (TokenValue) unquoted2;
            return tv1.value.equals(tv2.value);
        }
        
        // Оба развёрнутые списки
        if (unquoted1 instanceof List && unquoted2 instanceof List) {
            return compareListsRecursive((List<?>) unquoted1, (List<?>) unquoted2);
        }
        
        return false;
    }
    
    /**
     * Развёртывает quoted значение
     * (quote X) -> X
     * X -> X
     */
    private Object unquote(Object obj) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                String opName = getOperatorName(list.get(0));
                if ("quote".equals(opName) && list.size() > 1) {
                    return list.get(1);  // Развёртываем quoted значение
                }
            }
        }
        return obj;
    }
    
    /**
     * Проверяет, является ли объект атомом (IDENTIFIER TokenValue)
     */
    private boolean isAtomToken(Object obj) {
        if (!(obj instanceof TokenValue)) return false;
        TokenValue tv = (TokenValue) obj;
        return "IDENTIFIER".equals(tv.type);
    }
    
    /**
     * Рекурсивно сравнивает два списка
     */
    private boolean compareListsRecursive(List<?> list1, List<?> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        
        for (int i = 0; i < list1.size(); i++) {
            Object elem1 = list1.get(i);
            Object elem2 = list2.get(i);
            
            if (elem1 instanceof List && elem2 instanceof List) {
                if (!compareListsRecursive((List<?>) elem1, (List<?>) elem2)) {
                    return false;
                }
            } else if (elem1 instanceof TokenValue && elem2 instanceof TokenValue) {
                TokenValue tv1 = (TokenValue) elem1;
                TokenValue tv2 = (TokenValue) elem2;
                
                if (!tv1.type.equals(tv2.type) || !tv1.value.equals(tv2.value)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isLiteral(Object obj) {
        if (!(obj instanceof TokenValue)) return false;
        String type = ((TokenValue) obj).type;
        return "INT".equals(type) || "REAL".equals(type) || "BOOL".equals(type) || "NULL".equals(type);
    }
    
    private double getLiteralValue(Object obj) {
        Object unquoted = unquote(obj);
        TokenValue tv = (TokenValue) unquoted;
        if ("INT".equals(tv.type)) return ((Integer) tv.value).doubleValue();
        if ("REAL".equals(tv.type)) return (Double) tv.value;
        return 0.0;
    }
    
    private boolean getBoolValue(Object obj) {
        Object unquoted = unquote(obj);
        if (unquoted instanceof TokenValue) {
            TokenValue tv = (TokenValue) unquoted;
            if ("BOOL".equals(tv.type)) return (Boolean) tv.value;
        }
        return false;
    }
    
    private Object foldArithmetic(Object arg1, Object arg2, 
                                   java.util.function.BiFunction<Double, Double, Double> op) {
        double v1 = getLiteralValue(arg1);
        double v2 = getLiteralValue(arg2);
        Double result = op.apply(v1, v2);
        if (result == null) return null;
        
        // Проверяем тип первого аргумента (после развёртки)
        Object unquoted1 = unquote(arg1);
        TokenValue tv1 = (TokenValue) unquoted1;
        Object unquoted2 = unquote(arg2);
        TokenValue tv2 = (TokenValue) unquoted2;
        
        if ("INT".equals(tv1.type) && "INT".equals(tv2.type) && result == Math.floor(result)) {
            return new TokenValue("INT", result.intValue());
        }
        return new TokenValue("REAL", result);
    }
    
    private boolean isInt(Object obj) {
        Object unquoted = unquote(obj);
        return unquoted instanceof TokenValue && "INT".equals(((TokenValue) unquoted).type);
    }
    
    private boolean isReal(Object obj) {
        Object unquoted = unquote(obj);
        return unquoted instanceof TokenValue && "REAL".equals(((TokenValue) unquoted).type);
    }
    
    private boolean isBool(Object obj) {
        Object unquoted = unquote(obj);
        return unquoted instanceof TokenValue && "BOOL".equals(((TokenValue) unquoted).type);
    }
    
    private boolean isNull(Object obj) {
        Object unquoted = unquote(obj);
        return unquoted instanceof TokenValue && "NULL".equals(((TokenValue) unquoted).type);
    }
    
    private boolean isAtom(Object obj) {
        Object unquoted = unquote(obj);
        return unquoted instanceof TokenValue && "IDENTIFIER".equals(((TokenValue) unquoted).type);
    }
    
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
