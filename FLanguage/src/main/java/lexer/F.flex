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
QUOTE      = "quote"
SETQ       = "setq"
FUNC       = "func"
LAMBDA     = "lambda"
PROG       = "prog"
COND       = "cond"
WHILE      = "while"
RETURN     = "return"
BREAK      = "break"
PLUS       = "plus"
MINUS      = "minus"
TIMES      = "times"
DIVIDE     = "divide"
HEAD       = "head"
TAIL       = "tail"
CONS       = "cons"
EQUAL      = "equal"
NONEQUAL   = "nonequal"
LESS       = "less"
LESSEQ     = "lesseq"
GREATER    = "greater"
GREATEREQ  = "greatereq"
ISINT      = "isint"
ISREAL     = "isreal"
ISBOOL     = "isbool"
ISNULL     = "isnull"
ISATOM     = "isatom"
ISLIST     = "islist"
AND        = "and"
OR         = "or"
XOR        = "xor"
NOT        = "not"
EVAL       = "eval"

%%

{WHITESPACE}              { /* skip */ }

"("                       { yylval = yytext(); return parser.FParser.Lexer.tkLPAREN; }
")"                       { yylval = yytext(); return parser.FParser.Lexer.tkRPAREN; }
"'"                       { yylval = yytext(); return parser.FParser.Lexer.tkQUOTE; }

{BOOLEAN}                 { yylval = Boolean.parseBoolean(yytext()); return parser.FParser.Lexer.tkBOOL; }
{NULL}                    { yylval = null; return parser.FParser.Lexer.tkNULL; }

/* REAL before INT; with optional sign */
[+-]?{REAL}               { yylval = Double.parseDouble(yytext()); return parser.FParser.Lexer.tkREAL; }
[+-]?{INT}                { yylval = Integer.parseInt(yytext()); return parser.FParser.Lexer.tkINT; }

{QUOTE}                   { yylval = yytext(); return parser.FParser.Lexer.tkQUOTE; }
{SETQ}                    { yylval = yytext(); return parser.FParser.Lexer.tkSETQ; }
{FUNC}                    { yylval = yytext(); return parser.FParser.Lexer.tkFUNC; }
{LAMBDA}                  { yylval = yytext(); return parser.FParser.Lexer.tkLAMBDA; }
{PROG}                    { yylval = yytext(); return parser.FParser.Lexer.tkPROG; }
{COND}                    { yylval = yytext(); return parser.FParser.Lexer.tkCOND; }
{WHILE}                   { yylval = yytext(); return parser.FParser.Lexer.tkWHILE; }
{RETURN}                  { yylval = yytext(); return parser.FParser.Lexer.tkRETURN; }
{BREAK}                   { yylval = yytext(); return parser.FParser.Lexer.tkBREAK; }
{PLUS}                    { yylval = yytext(); return parser.FParser.Lexer.tkPLUS; }
{MINUS}                   { yylval = yytext(); return parser.FParser.Lexer.tkMINUS; }
{TIMES}                   { yylval = yytext(); return parser.FParser.Lexer.tkTIMES; }
{DIVIDE}                  { yylval = yytext(); return parser.FParser.Lexer.tkDIVIDE; }
{HEAD}                    { yylval = yytext(); return parser.FParser.Lexer.tkHEAD; }
{TAIL}                    { yylval = yytext(); return parser.FParser.Lexer.tkTAIL; }
{CONS}                    { yylval = yytext(); return parser.FParser.Lexer.tkCONS; }
{EQUAL}                   { yylval = yytext(); return parser.FParser.Lexer.tkEQUAL; }
{NONEQUAL}                { yylval = yytext(); return parser.FParser.Lexer.tkNONEQUAL; }
{LESS}                    { yylval = yytext(); return parser.FParser.Lexer.tkLESS; }
{LESSEQ}                  { yylval = yytext(); return parser.FParser.Lexer.tkLESSEQ; }
{GREATER}                 { yylval = yytext(); return parser.FParser.Lexer.tkGREATER; }
{GREATEREQ}               { yylval = yytext(); return parser.FParser.Lexer.tkGREATEREQ; }
{ISINT}                   { yylval = yytext(); return parser.FParser.Lexer.tkISINT; }
{ISREAL}                  { yylval = yytext(); return parser.FParser.Lexer.tkISREAL; }
{ISBOOL}                  { yylval = yytext(); return parser.FParser.Lexer.tkISBOOL; }
{ISNULL}                  { yylval = yytext(); return parser.FParser.Lexer.tkISNULL; }
{ISATOM}                  { yylval = yytext(); return parser.FParser.Lexer.tkISATOM; }
{ISLIST}                  { yylval = yytext(); return parser.FParser.Lexer.tkISLIST; }
{AND}                     { yylval = yytext(); return parser.FParser.Lexer.tkAND; }
{OR}                      { yylval = yytext(); return parser.FParser.Lexer.tkOR; }
{XOR}                     { yylval = yytext(); return parser.FParser.Lexer.tkXOR; }
{NOT}                     { yylval = yytext(); return parser.FParser.Lexer.tkNOT; }
{EVAL}                    { yylval = yytext(); return parser.FParser.Lexer.tkEVAL; }

{IDENT}                   { yylval = yytext(); return parser.FParser.Lexer.tkIDENT; }

/* unexpected char */
. { 
  yyerror("Illegal character: " + yytext());
  throw new RuntimeException("Scanner error: Illegal character: " + yytext());
}
