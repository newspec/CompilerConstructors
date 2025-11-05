package analyzer;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {
    private List<String> warnings = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    
    public AnalysisResult analyze(Object ast) {
        // Phase 1: Semantic Checks (non-modifying)
        runChecks(ast);
        
        // Phase 2: Optimizations (modifying)
        Object optimized = runOptimizations(ast);
        
        return new AnalysisResult(optimized, warnings, errors);
    }
    
    private void runChecks(Object ast) {
        try {
            // Check 1: Context-sensitive keyword usage
            ContextValidator contextValidator = new ContextValidator();
            contextValidator.validate(ast);
            
            // Check 2: Function arity checking
            ArityChecker arityChecker = new ArityChecker();
            arityChecker.collectFunctions(ast);
            arityChecker.checkCalls(ast);
            
            // Check 3: Built-in function argument counts
            BuiltinArityChecker builtinChecker = new BuiltinArityChecker();
            builtinChecker.check(ast);
            
            // Check 4: Undefined variable warnings
            UndefinedVariableChecker undefinedChecker = new UndefinedVariableChecker();
            List<String> undefined = undefinedChecker.check(ast);
            for (String var : undefined) {
                warnings.add("Variable '" + var + "' used before definition");
            }
            
        } catch (SemanticException e) {
            errors.add(e.getMessage());
        }
    }
    
    private Object runOptimizations(Object ast) {
        if (!errors.isEmpty()) {
            // Don't optimize if there are errors
            return ast;
        }
        
        Object optimized = ast;
        
        // Optimization 1: Constant folding
        ConstantFolder folder = new ConstantFolder();
        optimized = folder.fold(optimized);
        
        // Optimization 2: Dead code elimination after return
        DeadCodeEliminator deadCodeElim = new DeadCodeEliminator();
        optimized = deadCodeElim.eliminate(optimized);
        
        // Optimization 3: While loop simplification (remove dead while loops)
        WhileLoopOptimizer whileOptimizer = new WhileLoopOptimizer();
        optimized = whileOptimizer.optimize(optimized);
        
        // Optimization 4: Conditional simplification
        ConditionalSimplifier condSimplifier = new ConditionalSimplifier();
        optimized = condSimplifier.simplify(optimized);
        
        // Optimization 5: Unused variable removal
        UnusedVariableRemover unusedRemover = new UnusedVariableRemover();
        optimized = unusedRemover.remove(optimized);
        
        // Optimization 6: Unused function removal
        UnusedFunctionRemover unusedFuncRemover = new UnusedFunctionRemover();
        optimized = unusedFuncRemover.remove(optimized);
        
        return optimized;
    }
    
    public static class AnalysisResult {
        public final Object optimizedAST;
        public final List<String> warnings;
        public final List<String> errors;
        
        public AnalysisResult(Object ast, List<String> warnings, List<String> errors) {
            this.optimizedAST = ast;
            this.warnings = warnings;
            this.errors = errors;
        }
        
        public boolean hasErrors() {
            return !errors.isEmpty();
        }
    }
}