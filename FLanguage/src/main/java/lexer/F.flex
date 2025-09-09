package lexer;

%%

%class FScanner
%public
%unicode
%implements parser.FParser.Lexer
%function yylex
%int
%{
  private Object yylval;
  @Override
  public Object getLVal() { return yylval; }
  @Override
  public void yyerror(String s) { System.err.println("Scanner error: " + s); }
%}

/* Определения */
WHITESPACE = [ \t\r\n]+
LETTER     = [A-Za-z]
DECIMALDIGIT = [0-9]
INT        = {DECIMALDIGIT}+
REAL       = {DECIMALDIGIT}"."{DECIMALDIGIT}+
IDENT      = {LETTER}({LETTER}|{DECIMALDIGIT})*
BOOLEAN    = "true" | "false"
NULL       = "null"
KEYWORD    = "quote" | "setq" |  "func" | "lambda" | "prog" | "cond" | "while" | "return" | "break"
FUNC       = "plus" | "minus" | "times" | "divide" | "head" | "tail" | "cons" | "equal" | "nonequal" | "less" | "lesseq" | "greater" | "greatereq" | "isint" | "isreal" | "isbool" | "isnull" | "isatom" |"islist" | "and" | "or" | "xor" | "not" | "eval"

%%

{WHITESPACE}              { /* skip */ }

"("                       { yylval = null; return parser.FParser.Lexer.tkLPAREN; }
")"                       { yylval = null; return parser.FParser.Lexer.tkRPAREN; }
"'"                       { yylval = null; return parser.FParser.Lexer.tkQUOTE; }

{BOOLEAN}                 { yylval = Boolean.parseBoolean(yytext()); return parser.FParser.Lexer.tkBOOL; }
{NULL}                    { yylval = null; return parser.FParser.Lexer.tkNULL; }

/* REAL before INT; with optional sign */
[+-]?{REAL}               { yylval = Double.parseDouble(yytext()); return parser.FParser.Lexer.tkREAL; }
[+-]?{INT}                { yylval = Integer.parseInt(yytext()); return parser.FParser.Lexer.tkINT; }

{KEYWORD}                 { yylval = yytext(); return parser.FParser.Lexer.tkKEYWORD; }
{FUNC}                    { yylval = yytext(); return parser.FParser.Lexer.tkFUNC; }
{IDENT}                   { yylval = yytext(); return parser.FParser.Lexer.tkIDENT; }

/* unexpected char */
. { 
  yyerror("Illegal character: " + yytext());
  throw new RuntimeException("Scanner error: Illegal character: " + yytext());
}
