package analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import parser.TokenValue;


public class UndefinedVariableChecker {
    private final Set<String> globalDefinedAtoms = new HashSet<>();
    private final Set<String> undefinedErrors = new HashSet<>();
    private final Set<String> builtinFunctions = new HashSet<>();
    private final Map<String, Integer> definitionOrder = new HashMap<>();
    private int statementOrder = 0;


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
        definitionOrder.clear();
        statementOrder = 0;

        if (ast instanceof List<?>) {
            List<?> program = (List<?>) ast;
            
            // Единственный проход: обработка в порядке появления
            for (Object element : program) {
                checkElementIterative(element, new HashSet<>());
                statementOrder++;
            }
        }

        return new ArrayList<>(undefinedErrors);
    }


    private void checkElementIterative(Object root, Set<String> initialScope) {
        Deque<CheckFrame> stack = new LinkedList<>();
        stack.push(new CheckFrame(root, initialScope, CheckPhase.PROCESS));

        while (!stack.isEmpty()) {
            CheckFrame frame = stack.pop();
            Object element = frame.element;
            Set<String> localScope = frame.scope;

            if (element instanceof List<?>) {
                List<?> list = (List<?>) element;
                if (list.isEmpty()) continue;

                Object first = list.get(0);
                String opName = getOperatorName(first);
                boolean firstIsToken = first instanceof TokenValue;

                if ("quote".equals(opName)) {
                    continue;
                }

                if ("func".equals(opName)) {
                    // Сразу добавляем функцию в глобальные определения
                    if (list.size() > 1) {
                        Object nameObj = list.get(1);
                        String fname = extractNameFromTokenOrNode(nameObj);
                        if (fname != null) {
                            globalDefinedAtoms.add(fname);
                            definitionOrder.put(fname, statementOrder);
                        }
                    }

                    Set<String> funcScope = new HashSet<>(localScope);
                    if (list.size() > 2 && list.get(2) instanceof List<?>) {
                        for (Object p : (List<?>) list.get(2)) {
                            String pn = extractNameFromTokenOrNode(p);
                            if (pn != null) funcScope.add(pn);
                        }
                    }
                    if (list.size() > 3) {
                        stack.push(new CheckFrame(list.get(3), funcScope, CheckPhase.PROCESS));
                    }
                    continue;
                }

                if ("lambda".equals(opName)) {
                    Set<String> lambdaScope = new HashSet<>(localScope);
                    if (list.size() > 1 && list.get(1) instanceof List<?>) {
                        for (Object p : (List<?>) list.get(1)) {
                            String pn = extractNameFromTokenOrNode(p);
                            if (pn != null) lambdaScope.add(pn);
                        }
                    }
                    if (list.size() > 2) {
                        stack.push(new CheckFrame(list.get(2), lambdaScope, CheckPhase.PROCESS));
                    }
                    continue;
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
                        collectLocalDefinitionsIterative(list.get(i), progScope, shadowed);
                    }

                    for (int i = list.size() - 1; i >= bodyStart; i--) {
                        stack.push(new CheckFrame(list.get(i), progScope, CheckPhase.PROCESS));
                    }
                    continue;
                }

                if ("setq".equals(opName)) {
                    // Сначала проверяем значение
                    if (list.size() > 2) {
                        stack.push(new CheckFrame(list.get(2), localScope, CheckPhase.SETQ_CHECK_VALUE));
                    }
                    // Затем добавляем переменную в определения
                    if (list.size() > 1) {
                        Object varObj = list.get(1);
                        String varName = extractNameFromTokenOrNode(varObj);
                        if (varName != null) {
                            if (!globalDefinedAtoms.contains(varName)) {
                                globalDefinedAtoms.add(varName);
                                definitionOrder.put(varName, statementOrder);
                            }
                        }
                    }
                    continue;
                }

                // Обработка остальных элементов списка
                int startIndex = firstIsToken ? 1 : 0;
                for (int i = list.size() - 1; i >= startIndex; i--) {
                    stack.push(new CheckFrame(list.get(i), localScope, CheckPhase.PROCESS));
                }

            } else if (element instanceof TokenValue) {
                TokenValue tv = (TokenValue) element;
                if (isIdentifierToken(tv)) {
                    String name = tv.value != null ? tv.value.toString() : tv.type;
                    checkVariableUsage(name, localScope);
                }
            }
        }
    }


    private void checkVariableUsage(String name, Set<String> localScope) {
        if (name == null) return;

        // Если это встроенная функция, проверка не нужна
        if (builtinFunctions.contains(name)) return;

        // Если в локальной области видимости, все ОК
        if (localScope.contains(name)) return;

        // Если в глобальных определениях, проверяем порядок
        if (globalDefinedAtoms.contains(name)) {
            Integer defOrder = definitionOrder.get(name);
            if (defOrder != null && defOrder > statementOrder) {
                undefinedErrors.add(name);
            }
            return;
        }

        // Переменная не определена вообще
        undefinedErrors.add(name);
    }


    private void collectLocalDefinitionsIterative(Object root, Set<String> scope, Set<String> shadowed) {
        Deque<Object> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Object element = stack.pop();

            if (!(element instanceof List<?>)) continue;

            List<?> list = (List<?>) element;
            if (list.isEmpty()) continue;

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
                continue;
            }

            int startIndex = firstIsToken ? 1 : 0;
            for (int i = list.size() - 1; i >= startIndex; i--) {
                stack.push(list.get(i));
            }
        }
    }


    private boolean isIdentifierToken(TokenValue tv) {
        if (tv == null || tv.type == null) return false;
        String t = tv.type.toUpperCase().trim();
        return "IDENTIFIER".equals(t) || "ATOM".equals(t) || "SYMBOL".equals(t) || "NAME".equals(t);
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


    /**
     * Внутренний класс для представления кадра на стэке обхода
     */
    private static class CheckFrame {
        final Object element;
        final Set<String> scope;
        final CheckPhase phase;

        CheckFrame(Object element, Set<String> scope, CheckPhase phase) {
            this.element = element;
            this.scope = new HashSet<>(scope);
            this.phase = phase;
        }
    }


    /**
     * Перечисление фаз обработки элемента
     */
    private enum CheckPhase {
        PROCESS,              // Обычная обработка элемента
        SETQ_CHECK_VALUE      // Проверка значения в setq перед определением переменной
    }
}