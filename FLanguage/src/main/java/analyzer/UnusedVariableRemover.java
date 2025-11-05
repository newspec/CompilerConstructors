package analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import parser.TokenValue;

public class UnusedVariableRemover {
    public Object remove(Object ast) {
        // First pass: collect all variable definitions and usages
        Map<String, Integer> definitions = new HashMap<>();
        Map<String, Integer> usages = new HashMap<>();
        
        collectVars(ast, definitions, usages, false);
        
        // Second pass: remove unused setq statements
        Set<String> unused = new HashSet<>();
        for (String var : definitions.keySet()) {
            if (!usages.containsKey(var) || usages.get(var) == 0) {
                unused.add(var);
            }
        }
        
        return removeUnused(ast, unused);
    }
    
    private void collectVars(Object element, Map<String, Integer> defs, 
                            Map<String, Integer> uses, boolean inQuote) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue first = (TokenValue) list.get(0);
                
                if ("QUOTE".equals(first.type)) {
                    collectVars(list.get(1), defs, uses, true);
                    return;
                }
                
                if ("SETQ".equals(first.type)) {
                    TokenValue atom = (TokenValue) list.get(1);
                    String name = (String) atom.value;
                    defs.put(name, defs.getOrDefault(name, 0) + 1);
                    collectVars(list.get(2), defs, uses, false);
                    return;
                }
            }
            
            for (Object child : list) {
                collectVars(child, defs, uses, inQuote);
            }
        }
        else if (element instanceof TokenValue && !inQuote) {
            TokenValue tv = (TokenValue) element;
            if ("IDENTIFIER".equals(tv.type)) {
                String name = (String) tv.value;
                uses.put(name, uses.getOrDefault(name, 0) + 1);
            }
        }
    }
    
    private Object removeUnused(Object element, Set<String> unused) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            List<Object> result = new ArrayList<>();
            
            for (Object child : list) {
                if (child instanceof List) {
                    List<?> childList = (List<?>) child;
                    if (!childList.isEmpty() && childList.get(0) instanceof TokenValue) {
                        TokenValue first = (TokenValue) childList.get(0);
                        if ("SETQ".equals(first.type)) {
                            TokenValue atom = (TokenValue) childList.get(1);
                            String name = (String) atom.value;
                            if (unused.contains(name)) {
                                // Skip this setq
                                continue;
                            }
                        }
                    }
                }
                result.add(removeUnused(child, unused));
            }
            
            return result;
        }
        return element;
    }
}
