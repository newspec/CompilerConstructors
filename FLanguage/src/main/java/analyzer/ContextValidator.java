package analyzer;

import java.util.List;
import parser.TokenValue;

public class ContextValidator {
    private int functionDepth = 0;  // tracks func/lambda/prog nesting
    private int loopDepth = 0;      // tracks while nesting
    
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
            if (first instanceof TokenValue) {
                String type = ((TokenValue) first).type;
                
                switch (type) {
                    case "RETURN":
                        if (functionDepth == 0) {
                            throw new SemanticException("'return' outside function context");
                        }
                        if (list.size() > 1) {
                            validateElement(list.get(1)); // validate return expression
                        }
                        break;
                        
                    case "BREAK":
                        if (loopDepth == 0) {
                            throw new SemanticException("'break' outside loop context");
                        }
                        break;
                        
                    case "FUNC":
                        // (func name (params...) body)
                        functionDepth++;
                        if (list.size() > 3) {
                            validateElement(list.get(3)); // validate body
                        }
                        functionDepth--;
                        break;
                        
                    case "LAMBDA":
                        // (lambda (params...) body)
                        functionDepth++;
                        if (list.size() > 2) {
                            validateElement(list.get(2)); // validate body
                        }
                        functionDepth--;
                        break;
                        
                    case "PROG":
                        // (prog (local-vars...) body-elements...)
                        functionDepth++;
                        if (list.size() > 2) {
                            for (int i = 2; i < list.size(); i++) {
                                validateElement(list.get(i)); // validate body elements
                            }
                        }
                        functionDepth--;
                        break;
                        
                    case "WHILE":
                        loopDepth++;
                        if (list.size() > 1) {
                            validateElement(list.get(1)); // condition
                        }
                        if (list.size() > 2) {
                            validateElement(list.get(2)); // body
                        }
                        loopDepth--;
                        break;
                        
                    default:
                        // validate all sub-elements
                        for (int i = 1; i < list.size(); i++) {
                            validateElement(list.get(i));
                        }
                }
            }
        }
    }
}