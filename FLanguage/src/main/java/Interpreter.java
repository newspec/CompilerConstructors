import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import parser.TokenValue;

public class Interpreter {
    private final Map<String, Object> globalContext = new HashMap<>();
    private final Deque<Map<String, Object>> contextStack = new LinkedList<>();
    private final Deque<LoopContext> loopStack = new LinkedList<>();
    private boolean returnFlag = false;
    private Object returnValue = null;
    private boolean breakFlag = false;
    
    /**
     * Represents a built-in function by name
     */
    public static class BuiltinFunction {
        public final String name;
        
        public BuiltinFunction(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return "<builtin:" + name + ">";
        }
    }
    
    /**
     * Represents a user-defined function
     */
    public static class UserFunction {
        public final List<?> params;
        public final Object body;
        
        public UserFunction(List<?> params, Object body) {
            this.params = params;
            this.body = body;
        }
    }
    
    /**
     * Represents loop context for break statement
     */
    private static class LoopContext {
    }
    
    private List<Object> accumulatedResults = new ArrayList<>();

    public List<Object> getAccumulatedResults() {
        return accumulatedResults;
    }

    public List<Object> interpretAll(Object ast) throws RuntimeException {
        accumulatedResults.clear();  // Очищаем перед новым запуском
        RuntimeException lastError = null;
        
        if (ast instanceof List) {
            List<?> program = (List<?>) ast;
            for (Object element : program) {
                boolean isDefinition = isDefinitionForm(element);
                
                try {
                    Object result = evaluate(element);
                    if (!isDefinition) {
                        accumulatedResults.add(result);  // Накапливаем здесь
                    }
                    if (returnFlag) break;
                } catch (RuntimeException e) {
                    lastError = e;
                    break;
                }
            }
        } else {
            try {
                Object result = evaluate(ast);
                accumulatedResults.add(result);
            } catch (RuntimeException e) {
                lastError = e;
            }
        }
        
        if (lastError != null) {
            throw lastError;
        }
        
        return accumulatedResults;
    }
    
    /**
     * Checks if an element is a definition form (func or setq)
     */
    private boolean isDefinitionForm(Object element) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            if (list.isEmpty()) return false;
            
            String opName = getOperatorName(list.get(0));
            return "func".equals(opName) || "setq".equals(opName);
        }
        return false;
    }
    
    /**
     * Evaluates an element. Throws RuntimeException on any error.
     */
    private Object evaluate(Object element) {
        try {
            // Null check
            if (element == null) {
                return null;
            }
            
            // Handle plain strings as identifiers
            if (element instanceof String) {
                String name = (String) element;
                
                // Check if it's a built-in function name
                if (isBuiltinFunction(name)) {
                    return new BuiltinFunction(name);
                }
                
                // Otherwise try to look up as variable
                Object value = lookupVariable(name);
                if (value == null) {
                    throw new RuntimeException("Undefined variable: " + name);
                }
                return value;
            }
            
            // Atoms and literals
            if (element instanceof TokenValue) {
                return evaluateToken((TokenValue) element);
            }
            
            // Lists
            if (element instanceof List) {
                List<?> list = (List<?>) element;
                if (list.isEmpty()) return null;
                
                Object first = list.get(0);
                String opName = getOperatorName(first);
                
                // If no operator name, it's just an empty expression
                if (opName == null || opName.isEmpty()) {
                    return null;
                }
                
                // Special forms
                switch (opName) {
                    case "quote":
                        return handleQuote(list);
                    case "setq":
                        return handleSetq(list);
                    case "func":
                        return handleFunc(list);
                    case "lambda":
                        return handleLambda(list);
                    case "prog":
                        return handleProg(list);
                    case "cond":
                        return handleCond(list);
                    case "while":
                        return handleWhile(list);
                    case "return":
                        return handleReturn(list);
                    case "break":
                        return handleBreak(list);
                    default:
                        // Regular function call
                        return handleFunctionCall(list);
                }
            }
            
            return element;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during evaluation: " + e.getMessage(), e);
        }
    }
    
    /**
     * Evaluates TokenValue elements (atoms and literals).
     * Throws RuntimeException on any error.
     */
    private Object evaluateToken(TokenValue token) {
        if (token == null) {
            throw new RuntimeException("Token is null");
        }
        
        try {
            if ("NULL".equals(token.type)) {
                return null;
            }
            
            // Literals represent themselves
            if ("INT".equals(token.type)) {
                if (token.value instanceof Integer) {
                    return token.value;
                }
                if (token.value instanceof String) {
                    try {
                        return Integer.parseInt((String) token.value);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid integer literal: " + token.value);
                    }
                }
                throw new RuntimeException("Invalid INT token value type: " + token.value.getClass().getName());
            }
            
            if ("REAL".equals(token.type)) {
                if (token.value instanceof Double) {
                    return token.value;
                }
                if (token.value instanceof String) {
                    try {
                        return Double.parseDouble((String) token.value);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid real literal: " + token.value);
                    }
                }
                throw new RuntimeException("Invalid REAL token value type: " + token.value.getClass().getName());
            }
            
            if ("BOOL".equals(token.type)) {
                if (token.value instanceof Boolean) {
                    return token.value;
                }
                if (token.value instanceof String) {
                    String boolStr = ((String) token.value).toLowerCase();
                    if ("true".equals(boolStr)) return true;
                    if ("false".equals(boolStr)) return false;
                    throw new RuntimeException("Invalid boolean literal: " + token.value);
                }
                throw new RuntimeException("Invalid BOOL token value type: " + token.value.getClass().getName());
            }
            
            // Identifiers or built-in function tokens - look up their value
            if ("IDENTIFIER".equals(token.type) || isBuiltinFunctionToken(token.type)) {
                String name;
                
                if ("IDENTIFIER".equals(token.type)) {
                    if (!(token.value instanceof String)) {
                        throw new RuntimeException("Invalid IDENTIFIER token value type: " + token.value.getClass().getName());
                    }
                    name = (String) token.value;
                } else {
                    // Built-in function token (tkPLUS, tkMINUS, etc.) - convert to function name
                    name = tokenTypeToFunctionName(token.type);
                }
                
                // Check if it's a built-in function name
                if (isBuiltinFunction(name)) {
                    return new BuiltinFunction(name);
                }
                
                Object value = lookupVariable(name);
                if (value == null) {
                    throw new RuntimeException("Undefined variable: " + name);
                }
                return value;
            }
            
            throw new RuntimeException("Unknown token type: " + token.type);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating token: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if token type is a built-in function token
     */
    private boolean isBuiltinFunctionToken(String tokenType) {
        return tokenType.matches("PLUS|MINUS|TIMES|DIVIDE|HEAD|TAIL|CONS|EQUAL|NONEQUAL|" +
            "LESS|LESSEQ|GREATER|GREATEREQ|ISINT|ISREAL|ISBOOL|ISNULL|ISATOM|ISLIST|" +
            "AND|OR|XOR|NOT|EVAL");
    }

    /**
     * Converts token type to function name
     */
    private String tokenTypeToFunctionName(String tokenType) {
        // Convert tkPLUS -> plus, tkMINUS -> minus, etc.
        return tokenType.toLowerCase();
    }

    
    /**
     * ( quote Element )
     * Returns argument without evaluating it
     */
    private Object handleQuote(List<?> list) {
        try {
            if (list.size() < 2) {
                throw new RuntimeException("quote requires exactly one argument, got " + (list.size() - 1));
            }
            return list.get(1);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in quote: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( setq Atom Element )
     * Assigns value to atom, creating it if necessary
     */
    private Object handleSetq(List<?> list) {
        try {
            if (list.size() < 3) {
                throw new RuntimeException("setq requires exactly two arguments: atom and value, got " + (list.size() - 1));
            }
            
            Object varObj = list.get(1);
            String varName = extractIdentifier(varObj);
            Object value = evaluate(list.get(2));
            
            setVariable(varName, value);
            return value;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in setq: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( func Atom List Element )
     * Defines a new user-defined function with local context
     */
    private Object handleFunc(List<?> list) {
        try {
            if (list.size() < 4) {
                throw new RuntimeException("func requires exactly three arguments: name, parameters, and body, got " + (list.size() - 1));
            }
            
            String funcName = extractIdentifier(list.get(1));
            List<?> params = extractList(list.get(2));
            Object body = list.get(3);
            
            UserFunction userFunc = new UserFunction(params, body);
            setVariable(funcName, userFunc);
            return null;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in func: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( lambda List Element )
     * Defines an unnamed user-defined function
     */
    private Object handleLambda(List<?> list) {
        try {
            if (list.size() < 3) {
                throw new RuntimeException("lambda requires exactly two arguments: parameters and body, got " + (list.size() - 1));
            }
            
            List<?> params = extractList(list.get(1));
            Object body = list.get(2);
            
            return new UserFunction(params, body);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in lambda: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( prog List Element... )
     * Sequence evaluation with local context
     */
    private Object handleProg(List<?> list) {
        try {
            if (list.size() < 2) {
                throw new RuntimeException("prog requires at least two arguments: local variables and body, got " + (list.size() - 1));
            }
            
            // Create and push new local context
            Map<String, Object> localContext = new HashMap<>();
            contextStack.push(localContext);
            
            // Initialize local variables
            List<?> localVars = extractList(list.get(1));
            for (Object var : localVars) {
                String varName = extractIdentifier(var);
                localContext.put(varName, null);
            }
            
            Object result = null;
            
            try {
                // Evaluate all elements in prog body
                for (int i = 2; i < list.size(); i++) {
                    Object bodyElement = list.get(i);
                    
                    // If body element is a list of expressions, evaluate each one
                    if (bodyElement instanceof List) {
                        List<?> elements = (List<?>) bodyElement;
                        for (Object element : elements) {
                            result = evaluate(element);
                            if (returnFlag) break;
                        }
                    } else {
                        // Single element
                        result = evaluate(bodyElement);
                    }
                    
                    if (returnFlag) break;
                }
            } finally {
                // Pop context (always happens, even on error)
                contextStack.pop();
            }
            
            return result;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in prog: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( cond Element1 Element2 [ Element3 ] )
     * Conditional evaluation
     */
    private Object handleCond(List<?> list) {
        try {
            if (list.size() < 3) {
                throw new RuntimeException("cond requires at least two arguments: condition and then-branch, got " + (list.size() - 1));
            }
            
            Object condition = evaluate(list.get(1));
            boolean condResult = toBoolean(condition);
            
            if (condResult) {
                return evaluate(list.get(2));
            } else if (list.size() > 3) {
                return evaluate(list.get(3));
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in cond: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( while Element Element )
     * Loop while condition is true
     */
    private Object handleWhile(List<?> list) {
        try {
            if (list.size() < 2) {
                throw new RuntimeException("while requires exactly two arguments: condition and body, got " + (list.size() - 1));
            }
            
            // Push loop context
            loopStack.push(new LoopContext());
            
            try {
                while (true) {
                    // Evaluate condition
                    Object condition = evaluate(list.get(1));
                    if (!toBoolean(condition)) {
                        break;
                    }
                    
                    // Evaluate body
                    if (list.size() > 2) {
                        evaluate(list.get(2));
                    }
                    
                    // Check for break
                    if (breakFlag) {
                        breakFlag = false;
                        break;
                    }
                }
            } finally {
                loopStack.pop();
            }
            
            return null;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in while: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( return Element )
     * Exit from enclosing func, lambda, or prog
     */
    private Object handleReturn(List<?> list) {
        try {
            if (list.size() > 1) {
                returnValue = evaluate(list.get(1));
            } else {
                returnValue = null;
            }
            returnFlag = true;
            return returnValue;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in return: " + e.getMessage(), e);
        }
    }
    
    /**
     * ( break )
     * Exit from enclosing while loop
     */
    private Object handleBreak(List<?> list) {
        try {
            if (loopStack.isEmpty()) {
                throw new RuntimeException("break outside of while loop");
            }
            breakFlag = true;
            return null;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in break: " + e.getMessage(), e);
        }
    }
    
    /**
     * Handles function calls (both built-in and user-defined)
     */
    private Object handleFunctionCall(List<?> list) {
        try {
            if (list.isEmpty()) {
                return null;
            }
            
            Object firstElem = list.get(0);
            String funcName = getOperatorName(firstElem);
            
            // If we have a name to work with
            if (funcName != null && !funcName.isEmpty()) {
                // First, try to look up as a variable/function
                Object resolvedValue = lookupVariable(funcName);
                
                // If found and it's a callable object
                if (resolvedValue instanceof BuiltinFunction) {
                    String builtinName = ((BuiltinFunction) resolvedValue).name;
                    
                    // Evaluate all arguments
                    List<Object> args = new ArrayList<>();
                    for (int i = 1; i < list.size(); i++) {
                        args.add(evaluate(list.get(i)));
                    }
                    
                    // Call the built-in function
                    Object result = callBuiltinFunction(builtinName, args);
                    if (result != null || isBuiltinFunction(builtinName)) {
                        return result;
                    }
                    throw new RuntimeException("Failed to call built-in function: " + builtinName);
                }
                
                if (resolvedValue instanceof UserFunction) {
                    // Evaluate all arguments
                    List<Object> args = new ArrayList<>();
                    for (int i = 1; i < list.size(); i++) {
                        args.add(evaluate(list.get(i)));
                    }
                    return callUserFunction((UserFunction) resolvedValue, args);
                }
                
                // If resolvedValue is not null but not a function, it's an error
                if (resolvedValue != null && !(resolvedValue instanceof BuiltinFunction) && !(resolvedValue instanceof UserFunction)) {
                    throw new RuntimeException("Variable '" + funcName + "' is not a function, got: " + getTypeName(resolvedValue));
                }
                
                // If not found as variable, try as built-in function
                if (isBuiltinFunction(funcName)) {
                    // Evaluate all arguments
                    List<Object> args = new ArrayList<>();
                    for (int i = 1; i < list.size(); i++) {
                        args.add(evaluate(list.get(i)));
                    }
                    
                    Object result = callBuiltinFunction(funcName, args);
                    if (result != null) {
                        return result;
                    }
                }
                
                // If still not found, error
                throw new RuntimeException("Undefined function: " + funcName);
            }
            
            return null;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in function call: " + e.getMessage(), e);
        }
    }
    
    /**
     * Calls built-in functions (case-sensitive). Throws RuntimeException on any error.
     */
    private Object callBuiltinFunction(String name, List<Object> args) {
        try {
            // Arithmetic functions (case-sensitive)
            if ("plus".equals(name)) {
                checkArity(name, args, 2);
                return performArithmetic(args.get(0), args.get(1), '+');
            }
            if ("minus".equals(name)) {
                checkArity(name, args, 2);
                return performArithmetic(args.get(0), args.get(1), '-');
            }
            if ("times".equals(name)) {
                checkArity(name, args, 2);
                return performArithmetic(args.get(0), args.get(1), '*');
            }
            if ("divide".equals(name)) {
                checkArity(name, args, 2);
                double divisor = toNumber(args.get(1));
                if (Math.abs(divisor) < 1e-10) {
                    throw new RuntimeException("Division by zero");
                }
                double result = toNumber(args.get(0)) / divisor;
                return isInteger(args.get(0)) && isInteger(args.get(1)) ? (int) result : result;
            }
            
            // List operations (case-sensitive)
            if ("head".equals(name)) {
                checkArity(name, args, 1);
                List<?> list = extractList(args.get(0));
                if (list.isEmpty()) {
                    throw new RuntimeException("head of empty list");
                }
                return list.get(0);
            }
            if ("tail".equals(name)) {
                checkArity(name, args, 1);
                List<?> list = extractList(args.get(0));
                if (list.isEmpty()) {
                    throw new RuntimeException("tail of empty list");
                }
                return new ArrayList<>(list.subList(1, list.size()));
            }
            if ("cons".equals(name)) {
                checkArity(name, args, 2);
                List<Object> result = new ArrayList<>();
                result.add(args.get(0));
                List<?> rest = extractList(args.get(1));
                result.addAll(rest);
                return result;
            }
            
            // Comparison functions (case-sensitive)
            if ("equal".equals(name)) {
                checkArity(name, args, 2);
                return compareValues(args.get(0), args.get(1));
            }
            if ("nonequal".equals(name)) {
                checkArity(name, args, 2);
                return !compareValues(args.get(0), args.get(1));
            }
            if ("less".equals(name)) {
                checkArity(name, args, 2);
                return toNumber(args.get(0)) < toNumber(args.get(1));
            }
            if ("lesseq".equals(name)) {
                checkArity(name, args, 2);
                return toNumber(args.get(0)) <= toNumber(args.get(1));
            }
            if ("greater".equals(name)) {
                checkArity(name, args, 2);
                return toNumber(args.get(0)) > toNumber(args.get(1));
            }
            if ("greatereq".equals(name)) {
                checkArity(name, args, 2);
                return toNumber(args.get(0)) >= toNumber(args.get(1));
            }
            
            // Type predicates (case-sensitive)
            if ("isint".equals(name)) {
                checkArity(name, args, 1);
                return args.get(0) instanceof Integer;
            }
            if ("isreal".equals(name)) {
                checkArity(name, args, 1);
                return args.get(0) instanceof Double;
            }
            if ("isbool".equals(name)) {
                checkArity(name, args, 1);
                return args.get(0) instanceof Boolean;
            }
            if ("isnull".equals(name)) {
                checkArity(name, args, 1);
                return args.get(0) == null;
            }
            if ("isatom".equals(name)) {
                checkArity(name, args, 1);
                return args.get(0) instanceof TokenValue;
            }
            if ("islist".equals(name)) {
                checkArity(name, args, 1);
                return args.get(0) instanceof List;
            }
            
            // Logical operators (case-sensitive)
            if ("and".equals(name)) {
                checkArity(name, args, 2);
                return toBoolean(args.get(0)) && toBoolean(args.get(1));
            }
            if ("or".equals(name)) {
                checkArity(name, args, 2);
                return toBoolean(args.get(0)) || toBoolean(args.get(1));
            }
            if ("xor".equals(name)) {
                checkArity(name, args, 2);
                return toBoolean(args.get(0)) != toBoolean(args.get(1));
            }
            if ("not".equals(name)) {
                checkArity(name, args, 1);
                return !toBoolean(args.get(0));
            }
            
            // Evaluator
            if ("eval".equals(name)) {
                checkArity(name, args, 1);
                return evaluate(args.get(0));
            }
            
            return null;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error calling built-in function '" + name + "': " + e.getMessage(), e);
        }
    }
    
    /**
     * Calls user-defined functions. Throws RuntimeException on any error.
     */
    private Object callUserFunction(UserFunction func, List<Object> args) {
        try {
            // Create and push new local context
            Map<String, Object> localContext = new HashMap<>();
            contextStack.push(localContext);
            
            try {
                // Bind parameters
                List<?> params = func.params;
                if (args.size() != params.size()) {
                    throw new RuntimeException("Function expects " + params.size() + 
                        " arguments, got " + args.size());
                }
                
                for (int i = 0; i < params.size(); i++) {
                    String paramName = extractIdentifier(params.get(i));
                    localContext.put(paramName, args.get(i));
                }
                
                // Execute function body
                Object result = null;
                if (func.body != null) {
                    result = evaluate(func.body);
                }
                
                // Handle return flag
                if (returnFlag) {
                    returnFlag = false;
                    result = returnValue;
                    returnValue = null;
                }
                
                return result;
            } finally {
                // Pop context (always happens)
                contextStack.pop();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error calling user-defined function: " + e.getMessage(), e);
        }
    }
    
    /**
     * Performs arithmetic operations. Throws RuntimeException on error.
     */
    private Object performArithmetic(Object left, Object right, char op) {
        try {
            double l = toNumber(left);
            double r = toNumber(right);
            double result;
            
            switch (op) {
                case '+': result = l + r; break;
                case '-': result = l - r; break;
                case '*': result = l * r; break;
                case '/': result = l / r; break;
                default: throw new RuntimeException("Unknown arithmetic operator: " + op);
            }
            
            // Return integer if both operands are integers
            if (isInteger(left) && isInteger(right)) {
                return (int) result;
            }
            return result;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error in arithmetic operation: " + e.getMessage(), e);
        }
    }
    
    /**
     * Variable lookup.
     */
    private Object lookupVariable(String name) {
        try {
            // Check local contexts first (LIFO)
            for (Map<String, Object> context : contextStack) {
                if (context.containsKey(name)) {
                    return context.get(name);
                }
            }
            
            // Check global context
            return globalContext.get(name);
        } catch (Exception e) {
            throw new RuntimeException("Error looking up variable '" + name + "': " + e.getMessage(), e);
        }
    }
    
    /**
     * Variable assignment with proper shadowing.
     */
    private void setVariable(String name, Object value) {
        try {
            // If in local context and variable exists there, update it
            if (!contextStack.isEmpty()) {
                Map<String, Object> localContext = contextStack.peek();
                if (localContext.containsKey(name)) {
                    localContext.put(name, value);
                    return;
                }
            }
            
            // Otherwise, add to or update global context
            globalContext.put(name, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting variable '" + name + "': " + e.getMessage(), e);
        }
    }
    
    /**
     * Converts object to number. Throws RuntimeException on type mismatch.
     */
    private double toNumber(Object obj) {
        if (obj instanceof Integer) return ((Integer) obj).doubleValue();
        if (obj instanceof Double) return (Double) obj;
        throw new RuntimeException("Type error: expected number, got " + getTypeName(obj));
    }
    
    /**
     * Converts object to boolean.
     */
    private boolean toBoolean(Object obj) {
        if (obj instanceof Boolean) return (Boolean) obj;
        // In F, everything except false and null is true
        return obj != null && !Boolean.FALSE.equals(obj);
    }
    
    /**
     * Checks if object is an integer.
     */
    private boolean isInteger(Object obj) {
        return obj instanceof Integer;
    }
    
    /**
     * Compares two values for equality.
     */
    private boolean compareValues(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) return true;
        if (obj1 == null || obj2 == null) return false;
        
        if (obj1 instanceof Number && obj2 instanceof Number) {
            return Math.abs(toNumber(obj1) - toNumber(obj2)) < 1e-10;
        }
        
        return obj1.equals(obj2);
    }
    
    /**
     * Extracts identifier from object. Throws RuntimeException on error.
     */
    private String extractIdentifier(Object obj) {
        try {
            if (obj instanceof TokenValue) {
                TokenValue tv = (TokenValue) obj;
                if ("IDENTIFIER".equals(tv.type) && tv.value instanceof String) {
                    return (String) tv.value;
                }
            }
            throw new RuntimeException("Expected identifier, got " + getTypeName(obj));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error extracting identifier: " + e.getMessage(), e);
        }
    }
    
    /**
     * Extracts list from object. Throws RuntimeException on error.
     */
    private List<?> extractList(Object obj) {
        if (obj instanceof List) {
            return (List<?>) obj;
        }
        throw new RuntimeException("Type error: expected list, got " + getTypeName(obj));
    }
    
    /**
     * Gets operator name from first element of list.
     */
    private String getOperatorName(Object first) {
        try {
            if (first == null) {
                return "";
            }
            
            if (first instanceof TokenValue) {
                TokenValue tv = (TokenValue) first;
                if (tv.value instanceof String) {
                    return (String) tv.value;
                }
                // Для других типов токенов (PLUS, MINUS и т.д.)
                return tv.type.toLowerCase();
            }
            
            if (first instanceof String) {
                return (String) first;
            }
            
            return "";
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Gets human-readable type name.
     */
    private String getTypeName(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof Integer) return "integer";
        if (obj instanceof Double) return "real";
        if (obj instanceof Boolean) return "boolean";
        if (obj instanceof List) return "list";
        if (obj instanceof UserFunction) return "function";
        if (obj instanceof BuiltinFunction) return "builtin-function";
        if (obj instanceof TokenValue) return "atom";
        return obj.getClass().getSimpleName();
    }
    
    /**
     * Checks if function name is built-in (case-sensitive).
     */
    private boolean isBuiltinFunction(String name) {
        return name.matches("plus|minus|times|divide|head|tail|cons|equal|nonequal|" +
            "less|lesseq|greater|greatereq|isint|isreal|isbool|isnull|isatom|islist|" +
            "and|or|xor|not|eval");
    }
    
    /**
     * Checks function arity. Throws RuntimeException on mismatch.
     */
    private void checkArity(String funcName, List<Object> args, int expected) {
        if (args.size() != expected) {
            throw new RuntimeException("Function '" + funcName + "' expects " + expected + 
                " argument(s), got " + args.size());
        }
    }
}
