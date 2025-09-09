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
%token tkLPAREN tkRPAREN tkIDENT tkINT tkREAL tkBOOL tkNULL tkFUNC tkKEYWORD tkQUOTE

%%



program
    : /* empty */      { $$ = new ArrayList<Object>(); parseResult = $$; }
    | program element  { ((List<Object>) $1).add($2); $$ = $1; parseResult = $$; }
    ;

list
    : tkLPAREN list_elements tkRPAREN  { $$ = $2; }
    ;

list_elements
    : /* empty */                       { $$ = new ArrayList<Object>(); }
    | list_elements element             { ((List<Object>) $1).add($2); $$ = $1; }
    ;

element
    : atom     { $$ = $1; }
    | literal  { $$ = $1; }
    | list     { $$ = $1; }
    ;

atom
    : tkIDENT  { $$ = new parser.TokenValue("IDENTIFIER", $1); }
    | tkFUNC   { $$ = new parser.TokenValue("FUNCTION", $1); }
    | tkKEYWORD{ $$ = new parser.TokenValue("KEYWORD", $1); }
    | tkQUOTE element
        {
          List<Object> quoted = new ArrayList<Object>();
          quoted.add(new parser.TokenValue("KEYWORD", "quote"));
          quoted.add($2);
          $$ = quoted;
        }
    ;

literal
    : tkINT    { $$ = new parser.TokenValue("INT", $1); }
    | tkREAL   { $$ = new parser.TokenValue("REAL", $1); }
    | tkBOOL   { $$ = new parser.TokenValue("BOOL", $1); }
    | tkNULL   { $$ = new parser.TokenValue("NULL", null); }
    ;