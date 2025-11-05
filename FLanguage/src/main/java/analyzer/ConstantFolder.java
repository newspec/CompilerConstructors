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
            
            // First, fold all children
            List<Object> folded = new ArrayList<>();
            for (Object child : list) {
                folded.add(foldElement(child));
            }
            
            Object first = folded.get(0);
            if (!(first instanceof TokenValue)) return folded;
            
            TokenValue op = (TokenValue) first;
            
            // Try to fold arithmetic operations
            if (folded.size() == 3) {
                Object arg1 = folded.get(1);
                Object arg2 = folded.get(2);
                
                if (isLiteral(arg1) && isLiteral(arg2)) {
                    switch (op.type) {
                        case "PLUS":
                            return foldArithmetic(arg1, arg2, (a, b) -> a + b);
                        case "MINUS":
                            return foldArithmetic(arg1, arg2, (a, b) -> a - b);
                        case "TIMES":
                            return foldArithmetic(arg1, arg2, (a, b) -> a * b);
                        case "DIVIDE":
                            return foldArithmetic(arg1, arg2, (a, b) -> {
                                if (Math.abs(b) < 1e-10) return null; // avoid div by zero
                                return a / b;
                            });
                        case "LESS":
                            return new TokenValue("BOOL", getLiteralValue(arg1) < getLiteralValue(arg2));
                        case "GREATER":
                            return new TokenValue("BOOL", getLiteralValue(arg1) > getLiteralValue(arg2));
                        case "EQUAL":
                            return new TokenValue("BOOL", 
                                Math.abs(getLiteralValue(arg1) - getLiteralValue(arg2)) < 1e-10);
                        // Add more operators...
                    }
                }
            }
            
            return folded;
        }
        return element;
    }
    
    private boolean isLiteral(Object obj) {
        if (!(obj instanceof TokenValue)) return false;
        String type = ((TokenValue) obj).type;
        return "INT".equals(type) || "REAL".equals(type) || "BOOL".equals(type);
    }
    
    private double getLiteralValue(Object obj) {
        TokenValue tv = (TokenValue) obj;
        if ("INT".equals(tv.type)) return ((Integer) tv.value).doubleValue();
        if ("REAL".equals(tv.type)) return (Double) tv.value;
        return 0.0;
    }
    
    private Object foldArithmetic(Object arg1, Object arg2, 
                                   java.util.function.BiFunction<Double, Double, Double> op) {
        double v1 = getLiteralValue(arg1);
        double v2 = getLiteralValue(arg2);
        Double result = op.apply(v1, v2);
        if (result == null) return null;
        
        // Return INT if both args were INT and result is whole
        TokenValue tv1 = (TokenValue) arg1;
        TokenValue tv2 = (TokenValue) arg2;
        if ("INT".equals(tv1.type) && "INT".equals(tv2.type) && result == Math.floor(result)) {
            return new TokenValue("INT", result.intValue());
        }
        return new TokenValue("REAL", result);
    }
}
