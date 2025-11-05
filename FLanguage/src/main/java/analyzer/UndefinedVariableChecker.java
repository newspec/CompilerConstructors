package analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.TokenValue;

public class UndefinedVariableChecker {
    private Set<String> definedAtoms = new HashSet<>();
    private Set<String> usedAtoms = new HashSet<>();
    
    public List<String> check(Object ast) {
        collectDefinitions(ast, new HashSet<>());
        collectUsages(ast, new HashSet<>());
        
        List<String> undefined = new ArrayList<>();
        for (String used : usedAtoms) {
            if (!definedAtoms.contains(used)) {
                undefined.add(used);
            }
        }
        return undefined;
    }
    
    private void collectDefinitions(Object element, Set<String> currentScope) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue first = (TokenValue) list.get(0);
                
                if ("SETQ".equals(first.type)) {
                    // (setq atom value)
                    TokenValue atom = (TokenValue) list.get(1);
                    String atomName = (String) atom.value;
                    definedAtoms.add(atomName);
                    currentScope.add(atomName);
                }
                else if ("FUNC".equals(first.type)) {
                    // (func name (params...) body)
                    TokenValue name = (TokenValue) list.get(1);
                    definedAtoms.add((String) name.value);
                    
                    // Parameters are local
                    Set<String> localScope = new HashSet<>(currentScope);
                    List<?> params = (List<?>) list.get(2);
                    for (Object param : params) {
                        if (param instanceof TokenValue) {
                            localScope.add((String) ((TokenValue) param).value);
                        }
                    }
                    collectDefinitions(list.get(3), localScope); // body
                    return;
                }
                // Handle LAMBDA and PROG similarly
            }
            
            for (Object child : list) {
                collectDefinitions(child, currentScope);
            }
        }
    }
    
    private void collectUsages(Object element, Set<String> inQuote) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue first = (TokenValue) list.get(0);
                
                if ("QUOTE".equals(first.type)) {
                    // Quoted atoms don't count as usage
                    return;
                }
            }
            
            for (Object child : list) {
                collectUsages(child, inQuote);
            }
        }
        else if (element instanceof TokenValue) {
            TokenValue tv = (TokenValue) element;
            if ("IDENTIFIER".equals(tv.type) && !inQuote.contains(tv.value)) {
                usedAtoms.add((String) tv.value);
            }
        }
    }
}
