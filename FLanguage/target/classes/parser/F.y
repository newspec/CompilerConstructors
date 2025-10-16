%language "Java"
%define api.value.type {Object}
%define parse.error verbose
%define api.prefix {F}
%define api.package {parser}
%define api.parser.public               

%code imports {
  import java.util.List;
  import java.util.ArrayList;
  import java.io.Reader;
}

%code {
  /* хранит результат разбора (корневой AST, program) */
  private Object parseResult;
  public Object getParseResult() { return parseResult; }
}

/* --- Токены: указываем полные Java-типы для семантики --- */
%token tkLPAREN tkRPAREN tkQUOTE tkBOOL tkREAL tkINT tkNULL tkSETQ tkFUNC tkLAMBDA tkPROG tkCOND tkWHILE tkRETURN tkBREAK tkPLUS tkMINUS tkTIMES tkDIVIDE tkHEAD tkTAIL tkCONS tkEQUAL tkNONEQUAL tkLESS tkLESSEQ tkGREATER tkGREATEREQ tkISINT tkISREAL tkISBOOL tkISNULL tkISATOM tkISLIST tkAND tkOR tkXOR tkNOT tkEVAL tkIDENT

%%



program
    : /* empty */      { $$ = new ArrayList<Object>(); parseResult = $$; }
    | program element  { ((List<Object>) $1).add($2); $$ = $1; parseResult = $$; }
    ;

list
    : tkLPAREN list_elements tkRPAREN  { $$ = $2; }
    ;

list_elements
    : /* empty */  
        { $$ = new ArrayList<Object>(); }
    | list_elements element
        { ((List<Object>) $1).add($2); $$ = $1; }
    ;

literal
    : tkINT    { $$ = new parser.TokenValue("INT", $1); }
    | tkREAL   { $$ = new parser.TokenValue("REAL", $1); }
    | tkBOOL   { $$ = new parser.TokenValue("BOOL", $1); }
    | tkNULL   { $$ = new parser.TokenValue("NULL", null); }
    ;

atom
    : tkQUOTE    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkBOOL    { $$ = new parser.TokenValue("IDENTIFIER", $1); }   
    | tkREAL    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkINT { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkNULL    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkSETQ    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkFUNC    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkLAMBDA  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkPROG    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkCOND    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkWHILE   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkRETURN  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkBREAK   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkPLUS    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkMINUS   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkTIMES   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkDIVIDE  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkHEAD    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkTAIL    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkCONS    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkEQUAL   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkNONEQUAL    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkLESS    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkLESSEQ  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkGREATER { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkGREATEREQ   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkISINT   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkISREAL  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkISBOOL  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkISNULL  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkISATOM  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkISLIST  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkAND { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkOR  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkXOR { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkNOT { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkEVAL    { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkIDENT   { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    ;

element
    : list     { $$ = $1; }
    | special_form { $$ = $1; }
    | function { $$ = $1; }
    | literal  { $$ = $1; }
    | atom     { $$ = $1; }
    ;

special_form
    : quote_form
    | setq_form
    | func_form
    | lambda_form
    | prog_form
    | cond_form
    | while_form
    | return_form
    | break_form
    ;

quote_form
    : tkQUOTE element
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("QUOTE", "quote"));
            form.add($2);
            $$ = form;
        }
    | tkLPAREN tkQUOTE element tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("QUOTE", "quote"));
            form.add($3);
            $$ = form;
        }
    ;

setq_form
    : tkLPAREN tkSETQ atom element tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("SETQ", "setq"));
            form.add($3);
            form.add($4);
            $$ = form;
        }
    ;

func_form
    : tkLPAREN tkFUNC atom list element tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("FUNC", "func"));
            form.add($3);
            form.add($4);
            form.add($5);
            $$ = form;
        }
    ;

lambda_form
    : tkLPAREN tkLAMBDA list element tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("LAMBDA", "lambda"));
            form.add($3);
            form.add($4);
            $$ = form;
        }
    ;

prog_form
    : tkLPAREN tkPROG list list_elements tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("PROG", "prog"));
            form.add($3);
            form.add($4);
            $$ = form;
        }
    ;

cond_form
    : tkLPAREN tkCOND element element tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("COND", "cond"));
            form.add($3);
            form.add($4);
            $$ = form;
        }
    | tkLPAREN tkCOND element element element tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("COND", "cond"));
            form.add($3);
            form.add($4);
            form.add($5);
            $$ = form;
        }
    ;

while_form
    : tkLPAREN tkWHILE element element tkRPAREN
        {
            List<Object> form= new ArrayList<Object>();
            form.add(new parser.TokenValue("WHILE", "while"));
            form.add($3);
            form.add($4);
            $$ = form;
        }
    ;

return_form
    : tkLPAREN tkRETURN element tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("RETURN", "return"));
            form.add($3);
            $$ = form;
        }
    ;

break_form
    : tkLPAREN tkBREAK tkRPAREN
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("BREAK", "break"));
            $$ = form;
        }
    ;

function
    : arithmetic_function_call
    | operation_on_list_call
    | comparision_call
    | predicate_call
    | logical_operation_call
    | evaluation_call
    | generic_call
    ;

arithmetic_function_call
    : tkLPAREN tkPLUS element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("PLUS", "plus"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkMINUS element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("MINUS", "minus"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkTIMES element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("TIMES", "times"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkDIVIDE element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("DIVIDE", "divide"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    ;

operation_on_list_call
    : tkLPAREN tkHEAD element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("HEAD", "head"));
            call.add($3);
            $$ = call;
        }
    | tkLPAREN tkTAIL element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("TAIL", "tail"));
            call.add($3);
            $$ = call;
        }
    | tkLPAREN tkCONS element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("CONS", "cons"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    ;

comparision_call
    : tkLPAREN tkEQUAL element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("EQUAL", "equal"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkNONEQUAL element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("NONEQUAL", "nonequal"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkLESS element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("LESS", "less"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkLESSEQ element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("LESSEQ", "lesseq"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkGREATER element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("GREATER", "greater"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkGREATEREQ element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("GREATEREQ", "greatereq"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    ;

predicate_call
    : tkLPAREN tkISINT element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISINT", "isint"));
            call.add($3);
            $$ = call;
        }
    | tkLPAREN tkISREAL element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISREAL", "isreal"));
            call.add($3);
            $$ = call;
        }
    | tkLPAREN tkISBOOL element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISBOOL", "isbool"));
            call.add($3);
            $$ = call;
        }
    | tkLPAREN tkISNULL element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISNULL", "isnull"));
            call.add($3);
            $$ = call;
        }
    | tkLPAREN tkISATOM element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISATOM", "isatom"));
            call.add($3);
            $$ = call;
        }
    | tkLPAREN tkISLIST element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISLIST", "islist"));
            call.add($3);
            $$ = call;
        }
    ;

logical_operation_call
    : tkLPAREN tkAND element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("AND", "and"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkOR element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("OR", "or"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkXOR element element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("XOR", "xor"));
            call.add($3);
            call.add($4);
            $$ = call;
        }
    | tkLPAREN tkNOT element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("NOT", "not"));
            call.add($3);
            $$ = call;
        }
    ;

evaluation_call
    : tkLPAREN tkEVAL element tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("EVAL", "eval"));
            call.add($3);
            $$ = call;
        }
    ;

generic_call
    : tkLPAREN atom argument_list tkRPAREN
        {
            List<Object> call = new ArrayList<Object>();
            call.add($2);
            call.addAll((List<Object>) $3);
            $$ = call;
        }
    ;

argument_list
    : /* empty */                    { $$ = new ArrayList<Object>(); }
    | argument_list element          { ((List<Object>) $1).add($2); $$ = $1; }
    ;