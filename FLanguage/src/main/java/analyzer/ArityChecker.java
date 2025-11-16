package analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.TokenValue;

public class ArityChecker {
    private Map<String, Integer> userFunctions = new HashMap<>();  // func name -> arity
    private Map<String, Integer> lambdaVariables = new HashMap<>();  // variable name -> arity
    private List<String> errors = new ArrayList<>();
    
    public void collectFunctions(Object ast) throws SemanticException {
        if (ast instanceof List) {
            List<?> list = (List<?>) ast;
            for (Object element : list) {
                collectFunctionDefinitions(element);
            }
        }
    }
    
    private void collectFunctionDefinitions(Object element) throws SemanticException {
        if (!(element instanceof List)) return;
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return;
        
        String opName = getOperatorName(list.get(0));
        
        // func: (func name (params...) body)
        if ("func".equals(opName) && list.size() > 2) {
            Object nameObj = list.get(1);
            Object paramsObj = list.get(2);
            
            if (nameObj instanceof TokenValue && paramsObj instanceof List) {
                String funcName = (String) ((TokenValue) nameObj).value;
                List<?> params = (List<?>) paramsObj;
                userFunctions.put(funcName, params.size());
            }
        }
        
        // setq: (setq variable (lambda (params...) body))
        if ("setq".equals(opName) && list.size() > 2) {
            Object varObj = list.get(1);
            Object valueObj = list.get(2);
            
            if (varObj instanceof TokenValue && valueObj instanceof List) {
                String varName = (String) ((TokenValue) varObj).value;
                List<?> valueList = (List<?>) valueObj;
                
                if (!valueList.isEmpty()) {
                    String valueOp = getOperatorName(valueList.get(0));
                    if ("lambda".equals(valueOp) && valueList.size() > 1) {
                        Object paramsObj = valueList.get(1);
                        if (paramsObj instanceof List) {
                            List<?> params = (List<?>) paramsObj;
                            lambdaVariables.put(varName, params.size());
                        }
                    }
                }
            }
        }
    }
    
    public void checkCalls(Object ast) throws SemanticException {
        if (ast instanceof List) {
            List<?> list = (List<?>) ast;
            for (Object element : list) {
                checkElement(element);
            }
        }
    }
    
    private void checkElement(Object element) throws SemanticException {
        if (!(element instanceof List)) return;
        
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return;
        
        Object first = list.get(0);
        String opName = getOperatorName(first);
        
        // Проверяем саму lambda форму
        if ("lambda".equals(opName)) {
            if (list.size() < 2) {
                throw new SemanticException("'lambda' requires at least parameters list");
            }
            Object paramsObj = list.get(1);
            if (!(paramsObj instanceof List)) {
                throw new SemanticException("'lambda' parameters must be a list");
            }
            // Рекурсируем в тело lambda
            if (list.size() > 2) {
                checkElement(list.get(2));
            }
            return;
        }
        
        // Проверяем саму func форму
        if ("func".equals(opName)) {
            if (list.size() < 3) {
                throw new SemanticException("'func' requires name, parameters, and body");
            }
            Object paramsObj = list.get(2);
            if (!(paramsObj instanceof List)) {
                throw new SemanticException("'func' parameters must be a list");
            }
            // Рекурсируем в тело func
            if (list.size() > 3) {
                checkElement(list.get(3));
            }
            return;
        }
        
        // Проверяем quote
        if ("quote".equals(opName)) {
            return;  // Не проверяем внутри quote
        }
        
        // Если первый элемент - это вызов функции/переменной
        if (first instanceof TokenValue) {
            String name = ((TokenValue) first).value.toString();
            
            // Проверяем пользовательские функции
            if (userFunctions.containsKey(name)) {
                int expectedArity = userFunctions.get(name);
                int actualArity = list.size() - 1;
                
                if (actualArity != expectedArity) {
                    throw new SemanticException(
                        "Function '" + name + "' expects " + expectedArity + 
                        " arguments, got " + actualArity);
                }
            }
            
            // Проверяем lambda-переменные (переменные, которым присвоена lambda)
            if (lambdaVariables.containsKey(name)) {
                int expectedArity = lambdaVariables.get(name);
                int actualArity = list.size() - 1;
                
                if (actualArity != expectedArity) {
                    throw new SemanticException(
                        "Lambda '" + name + "' expects " + expectedArity + 
                        " arguments, got " + actualArity);
                }
            }
        }
        
        // Рекурсируем во все дети
        for (int i = 1; i < list.size(); i++) {
            checkElement(list.get(i));
        }
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