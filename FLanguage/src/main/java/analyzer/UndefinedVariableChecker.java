package analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import parser.TokenValue;

public class UndefinedVariableChecker {
    private final Set<String> globalDefinedAtoms = new HashSet<>();
    private final Set<String> undefinedErrors = new HashSet<>();
    private final Set<String> builtinFunctions = new HashSet<>();

    public UndefinedVariableChecker() {
        initializeBuiltins();
    }

    private void initializeBuiltins() {
        Collections.addAll(builtinFunctions,
            "plus", "minus", "times", "divide",
            "head", "tail", "cons",
            "equal", "nonequal", "less", "lesseq", "greater", "greatereq",
            "isint", "isreal", "isbool", "isnull", "isatom", "islist",
            "and", "or", "xor", "not", "eval",
            "while", "cond", "break", "return",
            "print", "quote", "setq", "func", "lambda", "prog",
            "list"
        );
    }

    public List<String> check(Object ast) {
        globalDefinedAtoms.clear();
        undefinedErrors.clear();

        if (ast instanceof List<?>) {
            List<?> program = (List<?>) ast;
            for (Object element : program) {
                collectGlobalDefinitions(element);
            }

            for (Object element : program) {
                checkUsages(element, new HashSet<>());
            }
        }

        return new ArrayList<>(undefinedErrors);
    }

    private void collectGlobalDefinitions(Object element) {
        if (!(element instanceof List<?>)) return;
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return;

        String opName = getOperatorName(list.get(0));

        if ("func".equals(opName) && list.size() > 1) {
            Object nameObj = list.get(1);
            String fname = extractNameFromTokenOrNode(nameObj);
            if (fname != null) {
                globalDefinedAtoms.add(fname);
            }
        }
        else if ("setq".equals(opName) && list.size() > 1) {
            Object varObj = list.get(1);
            String varName = extractNameFromTokenOrNode(varObj);
            if (varName != null) {
                globalDefinedAtoms.add(varName);
            }
        }
    }

    private void checkUsages(Object element, Set<String> localScope) {
        if (element instanceof List<?>) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return;

            Object first = list.get(0);
            String opName = getOperatorName(first);
            boolean firstIsToken = first instanceof TokenValue;

            if ("quote".equals(opName)) {
                return;
            }

            if ("func".equals(opName)) {
                Set<String> funcScope = new HashSet<>(localScope);
                if (list.size() > 2 && list.get(2) instanceof List<?>) {
                    for (Object p : (List<?>) list.get(2)) {
                        String pn = extractNameFromTokenOrNode(p);
                        if (pn != null) funcScope.add(pn);
                    }
                }
                if (list.size() > 3) checkUsages(list.get(3), funcScope);
                return;
            }

            if ("lambda".equals(opName)) {
                Set<String> lambdaScope = new HashSet<>(localScope);
                if (list.size() > 1 && list.get(1) instanceof List<?>) {
                    for (Object p : (List<?>) list.get(1)) {
                        String pn = extractNameFromTokenOrNode(p);
                        if (pn != null) lambdaScope.add(pn);
                    }
                }
                if (list.size() > 2) checkUsages(list.get(2), lambdaScope);
                return;
            }

            if ("prog".equals(opName)) {
                Set<String> progScope = new HashSet<>(localScope);
                Set<String> shadowed = new HashSet<>();

                if (list.size() > 1 && list.get(1) instanceof List<?>) {
                    for (Object v : (List<?>) list.get(1)) {
                        String vn = extractNameFromTokenOrNode(v);
                        if (vn != null) {
                            progScope.add(vn);
                            shadowed.add(vn);
                        }
                    }
                }

                int bodyStart = 2;
                for (int i = bodyStart; i < list.size(); i++) {
                    collectLocalDefinitions(list.get(i), progScope, shadowed);
                }

                for (int i = bodyStart; i < list.size(); i++) {
                    checkUsages(list.get(i), progScope);
                }
                return;
            }

            if ("setq".equals(opName)) {
                if (list.size() > 2) {
                    checkUsages(list.get(2), localScope);
                }
                return;
            }

            int startIndex = firstIsToken ? 1 : 0;
            for (int i = startIndex; i < list.size(); i++) {
                checkUsages(list.get(i), localScope);
            }

        } else if (element instanceof TokenValue) {
            TokenValue tv = (TokenValue) element;
            if (isIdentifierToken(tv)) {
                String name = tv.value != null ? tv.value.toString() : tv.type;
                if (!isDefinedAnywhere(name, localScope)) {
                    undefinedErrors.add(name);
                }
            }
        }
    }

    private void collectLocalDefinitions(Object element, Set<String> scope, Set<String> shadowed) {
        if (!(element instanceof List<?>)) return;
        List<?> list = (List<?>) element;
        if (list.isEmpty()) return;

        Object first = list.get(0);
        String opName = getOperatorName(first);
        boolean firstIsToken = first instanceof TokenValue;

        if ("setq".equals(opName) && list.size() > 1) {
            Object atomObj = list.get(1);
            String atomName = extractNameFromTokenOrNode(atomObj);
            if (atomName != null) {
                scope.add(atomName);
                if (globalDefinedAtoms.contains(atomName)) {
                    shadowed.add(atomName);
                }
            }
        }

        if ("prog".equals(opName) || "func".equals(opName) || "lambda".equals(opName)) {
            return;
        }

        int startIndex = firstIsToken ? 1 : 0;
        for (int i = startIndex; i < list.size(); i++) {
            collectLocalDefinitions(list.get(i), scope, shadowed);
        }
    }

    private boolean isIdentifierToken(TokenValue tv) {
        if (tv == null || tv.type == null) return false;
        String t = tv.type.toUpperCase().trim();
        return "IDENTIFIER".equals(t) || "ATOM".equals(t) || "SYMBOL".equals(t) || "NAME".equals(t);
    }

    private boolean isDefinedAnywhere(String name, Set<String> localScope) {
        if (name == null) return false;
        return builtinFunctions.contains(name) || localScope.contains(name) || globalDefinedAtoms.contains(name);
    }

    private String getOperatorName(Object first) {
        if (first == null) return "";
        if (first instanceof TokenValue) {
            TokenValue tv = (TokenValue) first;
            if (tv.value != null) return tv.value.toString().toLowerCase().trim();
            if (tv.type != null) return tv.type.toLowerCase().trim();
        }
        if (first instanceof List<?>) return "";
        return first.toString().toLowerCase().trim();
    }

    private String extractNameFromTokenOrNode(Object node) {
        if (node == null) return null;
        if (node instanceof TokenValue) {
            TokenValue tv = (TokenValue) node;
            if (tv.value != null) return tv.value.toString();
            if (tv.type != null) return tv.type;
        }
        if (node instanceof List<?>) {
            List<?> list = (List<?>) node;
            if (!list.isEmpty() && list.get(0) instanceof TokenValue) {
                TokenValue tv = (TokenValue) list.get(0);
                if (tv.value != null) return tv.value.toString();
                if (tv.type != null) return tv.type;
            }
        }
        return null;
    }
}