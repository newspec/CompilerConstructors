/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

   Copyright (C) 2007-2015, 2018-2021 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

package parser;



import java.text.MessageFormat;
import java.util.ArrayList;
/* "%code imports" blocks.  */
/* "src/main/java/parser/F.y":8  */

  import java.util.List;
  import java.util.ArrayList;
  import java.io.Reader;

/* "src/main/java/parser/FParser.java":51  */

/**
 * A Bison parser, automatically generated from <tt>src/main/java/parser/F.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class FParser
{
  /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "3.8.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";



  /**
   * True if verbose error messages are enabled.
   */
  private boolean yyErrorVerbose = true;

  /**
   * Whether verbose error messages are enabled.
   */
  public final boolean getErrorVerbose() { return yyErrorVerbose; }

  /**
   * Set the verbosity of error messages.
   * @param verbose True to request verbose error messages.
   */
  public final void setErrorVerbose(boolean verbose)
  { yyErrorVerbose = verbose; }




  public enum SymbolKind
  {
    S_YYEOF(0),                    /* "end of file"  */
    S_YYerror(1),                  /* error  */
    S_YYUNDEF(2),                  /* "invalid token"  */
    S_tkLPAREN(3),                 /* tkLPAREN  */
    S_tkRPAREN(4),                 /* tkRPAREN  */
    S_tkQUOTE(5),                  /* tkQUOTE  */
    S_tkBOOL(6),                   /* tkBOOL  */
    S_tkREAL(7),                   /* tkREAL  */
    S_tkINT(8),                    /* tkINT  */
    S_tkNULL(9),                   /* tkNULL  */
    S_tkSETQ(10),                  /* tkSETQ  */
    S_tkFUNC(11),                  /* tkFUNC  */
    S_tkLAMBDA(12),                /* tkLAMBDA  */
    S_tkPROG(13),                  /* tkPROG  */
    S_tkCOND(14),                  /* tkCOND  */
    S_tkWHILE(15),                 /* tkWHILE  */
    S_tkRETURN(16),                /* tkRETURN  */
    S_tkBREAK(17),                 /* tkBREAK  */
    S_tkPLUS(18),                  /* tkPLUS  */
    S_tkMINUS(19),                 /* tkMINUS  */
    S_tkTIMES(20),                 /* tkTIMES  */
    S_tkDIVIDE(21),                /* tkDIVIDE  */
    S_tkHEAD(22),                  /* tkHEAD  */
    S_tkTAIL(23),                  /* tkTAIL  */
    S_tkCONS(24),                  /* tkCONS  */
    S_tkEQUAL(25),                 /* tkEQUAL  */
    S_tkNONEQUAL(26),              /* tkNONEQUAL  */
    S_tkLESS(27),                  /* tkLESS  */
    S_tkLESSEQ(28),                /* tkLESSEQ  */
    S_tkGREATER(29),               /* tkGREATER  */
    S_tkGREATEREQ(30),             /* tkGREATEREQ  */
    S_tkISINT(31),                 /* tkISINT  */
    S_tkISREAL(32),                /* tkISREAL  */
    S_tkISBOOL(33),                /* tkISBOOL  */
    S_tkISNULL(34),                /* tkISNULL  */
    S_tkISATOM(35),                /* tkISATOM  */
    S_tkISLIST(36),                /* tkISLIST  */
    S_tkAND(37),                   /* tkAND  */
    S_tkOR(38),                    /* tkOR  */
    S_tkXOR(39),                   /* tkXOR  */
    S_tkNOT(40),                   /* tkNOT  */
    S_tkEVAL(41),                  /* tkEVAL  */
    S_tkIDENT(42),                 /* tkIDENT  */
    S_YYACCEPT(43),                /* $accept  */
    S_program(44),                 /* program  */
    S_definition_or_statement(45), /* definition_or_statement  */
    S_list(46),                    /* list  */
    S_list_elements(47),           /* list_elements  */
    S_element(48),                 /* element  */
    S_literal(49),                 /* literal  */
    S_quoted_identifier(50),       /* quoted_identifier  */
    S_statement(51),               /* statement  */
    S_special_form(52),            /* special_form  */
    S_quote_form(53),              /* quote_form  */
    S_setq_form(54),               /* setq_form  */
    S_func_form(55),               /* func_form  */
    S_lambda_form(56),             /* lambda_form  */
    S_prog_form(57),               /* prog_form  */
    S_cond_form(58),               /* cond_form  */
    S_while_form(59),              /* while_form  */
    S_return_form(60),             /* return_form  */
    S_break_form(61),              /* break_form  */
    S_function_call(62),           /* function_call  */
    S_func_operator(63),           /* func_operator  */
    S_argument_list(64);           /* argument_list  */


    private final int yycode_;

    SymbolKind (int n) {
      this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
      SymbolKind.S_YYEOF,
      SymbolKind.S_YYerror,
      SymbolKind.S_YYUNDEF,
      SymbolKind.S_tkLPAREN,
      SymbolKind.S_tkRPAREN,
      SymbolKind.S_tkQUOTE,
      SymbolKind.S_tkBOOL,
      SymbolKind.S_tkREAL,
      SymbolKind.S_tkINT,
      SymbolKind.S_tkNULL,
      SymbolKind.S_tkSETQ,
      SymbolKind.S_tkFUNC,
      SymbolKind.S_tkLAMBDA,
      SymbolKind.S_tkPROG,
      SymbolKind.S_tkCOND,
      SymbolKind.S_tkWHILE,
      SymbolKind.S_tkRETURN,
      SymbolKind.S_tkBREAK,
      SymbolKind.S_tkPLUS,
      SymbolKind.S_tkMINUS,
      SymbolKind.S_tkTIMES,
      SymbolKind.S_tkDIVIDE,
      SymbolKind.S_tkHEAD,
      SymbolKind.S_tkTAIL,
      SymbolKind.S_tkCONS,
      SymbolKind.S_tkEQUAL,
      SymbolKind.S_tkNONEQUAL,
      SymbolKind.S_tkLESS,
      SymbolKind.S_tkLESSEQ,
      SymbolKind.S_tkGREATER,
      SymbolKind.S_tkGREATEREQ,
      SymbolKind.S_tkISINT,
      SymbolKind.S_tkISREAL,
      SymbolKind.S_tkISBOOL,
      SymbolKind.S_tkISNULL,
      SymbolKind.S_tkISATOM,
      SymbolKind.S_tkISLIST,
      SymbolKind.S_tkAND,
      SymbolKind.S_tkOR,
      SymbolKind.S_tkXOR,
      SymbolKind.S_tkNOT,
      SymbolKind.S_tkEVAL,
      SymbolKind.S_tkIDENT,
      SymbolKind.S_YYACCEPT,
      SymbolKind.S_program,
      SymbolKind.S_definition_or_statement,
      SymbolKind.S_list,
      SymbolKind.S_list_elements,
      SymbolKind.S_element,
      SymbolKind.S_literal,
      SymbolKind.S_quoted_identifier,
      SymbolKind.S_statement,
      SymbolKind.S_special_form,
      SymbolKind.S_quote_form,
      SymbolKind.S_setq_form,
      SymbolKind.S_func_form,
      SymbolKind.S_lambda_form,
      SymbolKind.S_prog_form,
      SymbolKind.S_cond_form,
      SymbolKind.S_while_form,
      SymbolKind.S_return_form,
      SymbolKind.S_break_form,
      SymbolKind.S_function_call,
      SymbolKind.S_func_operator,
      SymbolKind.S_argument_list
    };

    static final SymbolKind get(int code) {
      return values_[code];
    }

    public final int getCode() {
      return this.yycode_;
    }

    /* Return YYSTR after stripping away unnecessary quotes and
       backslashes, so that it's suitable for yyerror.  The heuristic is
       that double-quoting is unnecessary unless the string contains an
       apostrophe, a comma, or backslash (other than backslash-backslash).
       YYSTR is taken from yytname.  */
    private static String yytnamerr_(String yystr)
    {
      if (yystr.charAt (0) == '"')
        {
          StringBuffer yyr = new StringBuffer();
          strip_quotes: for (int i = 1; i < yystr.length(); i++)
            switch (yystr.charAt(i))
              {
              case '\'':
              case ',':
                break strip_quotes;

              case '\\':
                if (yystr.charAt(++i) != '\\')
                  break strip_quotes;
                /* Fall through.  */
              default:
                yyr.append(yystr.charAt(i));
                break;

              case '"':
                return yyr.toString();
              }
        }
      return yystr;
    }

    /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
       First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
    private static final String[] yytname_ = yytname_init();
  private static final String[] yytname_init()
  {
    return new String[]
    {
  "\"end of file\"", "error", "\"invalid token\"", "tkLPAREN", "tkRPAREN",
  "tkQUOTE", "tkBOOL", "tkREAL", "tkINT", "tkNULL", "tkSETQ", "tkFUNC",
  "tkLAMBDA", "tkPROG", "tkCOND", "tkWHILE", "tkRETURN", "tkBREAK",
  "tkPLUS", "tkMINUS", "tkTIMES", "tkDIVIDE", "tkHEAD", "tkTAIL", "tkCONS",
  "tkEQUAL", "tkNONEQUAL", "tkLESS", "tkLESSEQ", "tkGREATER",
  "tkGREATEREQ", "tkISINT", "tkISREAL", "tkISBOOL", "tkISNULL", "tkISATOM",
  "tkISLIST", "tkAND", "tkOR", "tkXOR", "tkNOT", "tkEVAL", "tkIDENT",
  "$accept", "program", "definition_or_statement", "list", "list_elements",
  "element", "literal", "quoted_identifier", "statement", "special_form",
  "quote_form", "setq_form", "func_form", "lambda_form", "prog_form",
  "cond_form", "while_form", "return_form", "break_form", "function_call",
  "func_operator", "argument_list", null
    };
  }

    /* The user-facing name of this symbol.  */
    public final String getName() {
      return yytnamerr_(yytname_[yycode_]);
    }

  };


  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>FParser</tt>.
   */
  public interface Lexer {
    /* Token kinds.  */
    /** Token "end of file", to be returned by the scanner.  */
    static final int YYEOF = 0;
    /** Token error, to be returned by the scanner.  */
    static final int YYerror = 256;
    /** Token "invalid token", to be returned by the scanner.  */
    static final int YYUNDEF = 257;
    /** Token tkLPAREN, to be returned by the scanner.  */
    static final int tkLPAREN = 258;
    /** Token tkRPAREN, to be returned by the scanner.  */
    static final int tkRPAREN = 259;
    /** Token tkQUOTE, to be returned by the scanner.  */
    static final int tkQUOTE = 260;
    /** Token tkBOOL, to be returned by the scanner.  */
    static final int tkBOOL = 261;
    /** Token tkREAL, to be returned by the scanner.  */
    static final int tkREAL = 262;
    /** Token tkINT, to be returned by the scanner.  */
    static final int tkINT = 263;
    /** Token tkNULL, to be returned by the scanner.  */
    static final int tkNULL = 264;
    /** Token tkSETQ, to be returned by the scanner.  */
    static final int tkSETQ = 265;
    /** Token tkFUNC, to be returned by the scanner.  */
    static final int tkFUNC = 266;
    /** Token tkLAMBDA, to be returned by the scanner.  */
    static final int tkLAMBDA = 267;
    /** Token tkPROG, to be returned by the scanner.  */
    static final int tkPROG = 268;
    /** Token tkCOND, to be returned by the scanner.  */
    static final int tkCOND = 269;
    /** Token tkWHILE, to be returned by the scanner.  */
    static final int tkWHILE = 270;
    /** Token tkRETURN, to be returned by the scanner.  */
    static final int tkRETURN = 271;
    /** Token tkBREAK, to be returned by the scanner.  */
    static final int tkBREAK = 272;
    /** Token tkPLUS, to be returned by the scanner.  */
    static final int tkPLUS = 273;
    /** Token tkMINUS, to be returned by the scanner.  */
    static final int tkMINUS = 274;
    /** Token tkTIMES, to be returned by the scanner.  */
    static final int tkTIMES = 275;
    /** Token tkDIVIDE, to be returned by the scanner.  */
    static final int tkDIVIDE = 276;
    /** Token tkHEAD, to be returned by the scanner.  */
    static final int tkHEAD = 277;
    /** Token tkTAIL, to be returned by the scanner.  */
    static final int tkTAIL = 278;
    /** Token tkCONS, to be returned by the scanner.  */
    static final int tkCONS = 279;
    /** Token tkEQUAL, to be returned by the scanner.  */
    static final int tkEQUAL = 280;
    /** Token tkNONEQUAL, to be returned by the scanner.  */
    static final int tkNONEQUAL = 281;
    /** Token tkLESS, to be returned by the scanner.  */
    static final int tkLESS = 282;
    /** Token tkLESSEQ, to be returned by the scanner.  */
    static final int tkLESSEQ = 283;
    /** Token tkGREATER, to be returned by the scanner.  */
    static final int tkGREATER = 284;
    /** Token tkGREATEREQ, to be returned by the scanner.  */
    static final int tkGREATEREQ = 285;
    /** Token tkISINT, to be returned by the scanner.  */
    static final int tkISINT = 286;
    /** Token tkISREAL, to be returned by the scanner.  */
    static final int tkISREAL = 287;
    /** Token tkISBOOL, to be returned by the scanner.  */
    static final int tkISBOOL = 288;
    /** Token tkISNULL, to be returned by the scanner.  */
    static final int tkISNULL = 289;
    /** Token tkISATOM, to be returned by the scanner.  */
    static final int tkISATOM = 290;
    /** Token tkISLIST, to be returned by the scanner.  */
    static final int tkISLIST = 291;
    /** Token tkAND, to be returned by the scanner.  */
    static final int tkAND = 292;
    /** Token tkOR, to be returned by the scanner.  */
    static final int tkOR = 293;
    /** Token tkXOR, to be returned by the scanner.  */
    static final int tkXOR = 294;
    /** Token tkNOT, to be returned by the scanner.  */
    static final int tkNOT = 295;
    /** Token tkEVAL, to be returned by the scanner.  */
    static final int tkEVAL = 296;
    /** Token tkIDENT, to be returned by the scanner.  */
    static final int tkIDENT = 297;

    /** Deprecated, use YYEOF instead.  */
    public static final int EOF = YYEOF;


    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.
     */
    Object getLVal();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    int yylex() throws java.io.IOException;

    /**
     * Emit an errorin a user-defined way.
     *
     *
     * @param msg The string for the error message.
     */
     void yyerror(String msg);


  }


  /**
   * The object doing lexical analysis for us.
   */
  private Lexer yylexer;





  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public FParser(Lexer yylexer)
  {

    this.yylexer = yylexer;

  }



  private int yynerrs = 0;

  /**
   * The number of syntax errors so far.
   */
  public final int getNumberOfErrors() { return yynerrs; }

  /**
   * Print an error message via the lexer.
   *
   * @param msg The error message.
   */
  public final void yyerror(String msg) {
      yylexer.yyerror(msg);
  }



  private final class YYStack {
    private int[] stateStack = new int[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push(int state, Object value) {
      height++;
      if (size == height) {
        int[] newStateStack = new int[size * 2];
        System.arraycopy(stateStack, 0, newStateStack, 0, height);
        stateStack = newStateStack;

        Object[] newValueStack = new Object[size * 2];
        System.arraycopy(valueStack, 0, newValueStack, 0, height);
        valueStack = newValueStack;

        size *= 2;
      }

      stateStack[height] = state;
      valueStack[height] = value;
    }

    public final void pop() {
      pop(1);
    }

    public final void pop(int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (0 < num) {
        java.util.Arrays.fill(valueStack, height - num + 1, height + 1, null);
      }
      height -= num;
    }

    public final int stateAt(int i) {
      return stateStack[height - i];
    }

    public final Object valueAt(int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print(java.io.PrintStream out) {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++) {
        out.print(' ');
        out.print(stateStack[i]);
      }
      out.println();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).
   */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).
   */
  public static final int YYABORT = 1;



  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.
   */
  public static final int YYERROR = 2;

  /**
   * Internal return codes that are not supported for user semantic
   * actions.
   */
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;


  private int yyerrstatus_ = 0;


  /**
   * Whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.
   */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  /** Compute post-reduction state.
   * @param yystate   the current state
   * @param yysym     the nonterminal to push on the stack
   */
  private int yyLRGotoState(int yystate, int yysym) {
    int yyr = yypgoto_[yysym - YYNTOKENS_] + yystate;
    if (0 <= yyr && yyr <= YYLAST_ && yycheck_[yyr] == yystate)
      return yytable_[yyr];
    else
      return yydefgoto_[yysym - YYNTOKENS_];
  }

  private int yyaction(int yyn, YYStack yystack, int yylen)
  {
    /* If YYLEN is nonzero, implement the default value of the action:
       '$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    Object yyval = (0 < yylen) ? yystack.valueAt(yylen - 1) : yystack.valueAt(0);

    switch (yyn)
      {
          case 2: /* program: %empty  */
  if (yyn == 2)
    /* "src/main/java/parser/F.y":28  */
        { yyval = new ArrayList<Object>(); parseResult = yyval; };
  break;


  case 3: /* program: program definition_or_statement  */
  if (yyn == 3)
    /* "src/main/java/parser/F.y":30  */
        { ((List<Object>)yystack.valueAt (1)).add(yystack.valueAt (0)); yyval = yystack.valueAt (1); parseResult = yyval; };
  break;


  case 6: /* list: tkLPAREN list_elements tkRPAREN  */
  if (yyn == 6)
    /* "src/main/java/parser/F.y":41  */
        { yyval = yystack.valueAt (1); };
  break;


  case 7: /* list_elements: %empty  */
  if (yyn == 7)
    /* "src/main/java/parser/F.y":46  */
        { yyval = new ArrayList<Object>(); };
  break;


  case 8: /* list_elements: list_elements element  */
  if (yyn == 8)
    /* "src/main/java/parser/F.y":48  */
        { ((List<Object>)yystack.valueAt (1)).add(yystack.valueAt (0)); yyval = yystack.valueAt (1); };
  break;


  case 12: /* literal: tkINT  */
  if (yyn == 12)
    /* "src/main/java/parser/F.y":60  */
              { yyval = new parser.TokenValue("INT",    yystack.valueAt (0)); };
  break;


  case 13: /* literal: tkREAL  */
  if (yyn == 13)
    /* "src/main/java/parser/F.y":61  */
              { yyval = new parser.TokenValue("REAL",   yystack.valueAt (0)); };
  break;


  case 14: /* literal: tkBOOL  */
  if (yyn == 14)
    /* "src/main/java/parser/F.y":62  */
              { yyval = new parser.TokenValue("BOOL",   yystack.valueAt (0)); };
  break;


  case 15: /* literal: tkNULL  */
  if (yyn == 15)
    /* "src/main/java/parser/F.y":63  */
              { yyval = new parser.TokenValue("NULL",   null); };
  break;


  case 16: /* quoted_identifier: tkQUOTE tkIDENT  */
  if (yyn == 16)
    /* "src/main/java/parser/F.y":69  */
        {
          yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0));
        };
  break;


  case 17: /* quoted_identifier: tkIDENT  */
  if (yyn == 17)
    /* "src/main/java/parser/F.y":73  */
        {
          yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0));
        };
  break;


  case 29: /* quote_form: tkLPAREN tkQUOTE element tkRPAREN  */
  if (yyn == 29)
    /* "src/main/java/parser/F.y":100  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("QUOTE","quote"));
          f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 30: /* setq_form: tkLPAREN tkSETQ quoted_identifier element tkRPAREN  */
  if (yyn == 30)
    /* "src/main/java/parser/F.y":111  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("SETQ","setq"));
          f.add(yystack.valueAt (2));
          f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 31: /* func_form: tkLPAREN tkFUNC quoted_identifier list element tkRPAREN  */
  if (yyn == 31)
    /* "src/main/java/parser/F.y":123  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("FUNC","func"));
          f.add(yystack.valueAt (3));
          f.add(yystack.valueAt (2));
          f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 32: /* lambda_form: tkLPAREN tkLAMBDA list element tkRPAREN  */
  if (yyn == 32)
    /* "src/main/java/parser/F.y":136  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("LAMBDA","lambda"));
          f.add(yystack.valueAt (2));
          f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 33: /* prog_form: tkLPAREN tkPROG list list_elements tkRPAREN  */
  if (yyn == 33)
    /* "src/main/java/parser/F.y":148  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("PROG","prog"));
          f.add(yystack.valueAt (2));
          f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 34: /* cond_form: tkLPAREN tkCOND element element tkRPAREN  */
  if (yyn == 34)
    /* "src/main/java/parser/F.y":160  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("COND","cond"));
          f.add(yystack.valueAt (2)); f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 35: /* cond_form: tkLPAREN tkCOND element element element tkRPAREN  */
  if (yyn == 35)
    /* "src/main/java/parser/F.y":167  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("COND","cond"));
          f.add(yystack.valueAt (3)); f.add(yystack.valueAt (2)); f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 36: /* while_form: tkLPAREN tkWHILE element element tkRPAREN  */
  if (yyn == 36)
    /* "src/main/java/parser/F.y":178  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("WHILE","while"));
          f.add(yystack.valueAt (2)); f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 37: /* return_form: tkLPAREN tkRETURN element tkRPAREN  */
  if (yyn == 37)
    /* "src/main/java/parser/F.y":189  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("RETURN","return"));
          f.add(yystack.valueAt (1));
          yyval = f;
        };
  break;


  case 38: /* break_form: tkLPAREN tkBREAK tkRPAREN  */
  if (yyn == 38)
    /* "src/main/java/parser/F.y":200  */
        {
          List<Object> f = new ArrayList<>();
          f.add(new parser.TokenValue("BREAK","break"));
          yyval = f;
        };
  break;


  case 39: /* function_call: tkLPAREN func_operator argument_list tkRPAREN  */
  if (yyn == 39)
    /* "src/main/java/parser/F.y":210  */
        {
          List<Object> call = new ArrayList<>();
          call.add(new parser.TokenValue(yystack.valueAt (2).type(), yystack.valueAt (2).text()));
          call.addAll(yystack.valueAt (1));
          yyval = call;
        };
  break;


  case 40: /* func_operator: tkPLUS  */
  if (yyn == 40)
    /* "src/main/java/parser/F.y":220  */
                   { yyval = new parser.TokenValue("PLUS","plus"); };
  break;


  case 41: /* func_operator: tkMINUS  */
  if (yyn == 41)
    /* "src/main/java/parser/F.y":221  */
                   { yyval = new parser.TokenValue("MINUS","minus"); };
  break;


  case 42: /* func_operator: tkTIMES  */
  if (yyn == 42)
    /* "src/main/java/parser/F.y":222  */
                   { yyval = new parser.TokenValue("TIMES","times"); };
  break;


  case 43: /* func_operator: tkDIVIDE  */
  if (yyn == 43)
    /* "src/main/java/parser/F.y":223  */
                   { yyval = new parser.TokenValue("DIVIDE","divide"); };
  break;


  case 44: /* func_operator: tkHEAD  */
  if (yyn == 44)
    /* "src/main/java/parser/F.y":224  */
                   { yyval = new parser.TokenValue("HEAD","head"); };
  break;


  case 45: /* func_operator: tkTAIL  */
  if (yyn == 45)
    /* "src/main/java/parser/F.y":225  */
                   { yyval = new parser.TokenValue("TAIL","tail"); };
  break;


  case 46: /* func_operator: tkCONS  */
  if (yyn == 46)
    /* "src/main/java/parser/F.y":226  */
                   { yyval = new parser.TokenValue("CONS","cons"); };
  break;


  case 47: /* func_operator: tkEQUAL  */
  if (yyn == 47)
    /* "src/main/java/parser/F.y":227  */
                   { yyval = new parser.TokenValue("EQUAL","equal"); };
  break;


  case 48: /* func_operator: tkNONEQUAL  */
  if (yyn == 48)
    /* "src/main/java/parser/F.y":228  */
                   { yyval = new parser.TokenValue("NONEQUAL","nonequal"); };
  break;


  case 49: /* func_operator: tkLESS  */
  if (yyn == 49)
    /* "src/main/java/parser/F.y":229  */
                   { yyval = new parser.TokenValue("LESS","less"); };
  break;


  case 50: /* func_operator: tkLESSEQ  */
  if (yyn == 50)
    /* "src/main/java/parser/F.y":230  */
                   { yyval = new parser.TokenValue("LESSEQ","lesseq"); };
  break;


  case 51: /* func_operator: tkGREATER  */
  if (yyn == 51)
    /* "src/main/java/parser/F.y":231  */
                   { yyval = new parser.TokenValue("GREATER","greater"); };
  break;


  case 52: /* func_operator: tkGREATEREQ  */
  if (yyn == 52)
    /* "src/main/java/parser/F.y":232  */
                   { yyval = new parser.TokenValue("GREATEREQ","greatereq"); };
  break;


  case 53: /* func_operator: tkAND  */
  if (yyn == 53)
    /* "src/main/java/parser/F.y":233  */
                   { yyval = new parser.TokenValue("AND","and"); };
  break;


  case 54: /* func_operator: tkOR  */
  if (yyn == 54)
    /* "src/main/java/parser/F.y":234  */
                   { yyval = new parser.TokenValue("OR","or"); };
  break;


  case 55: /* func_operator: tkXOR  */
  if (yyn == 55)
    /* "src/main/java/parser/F.y":235  */
                   { yyval = new parser.TokenValue("XOR","xor"); };
  break;


  case 56: /* func_operator: tkNOT  */
  if (yyn == 56)
    /* "src/main/java/parser/F.y":236  */
                   { yyval = new parser.TokenValue("NOT","not"); };
  break;


  case 57: /* func_operator: tkISINT  */
  if (yyn == 57)
    /* "src/main/java/parser/F.y":237  */
                   { yyval = new parser.TokenValue("ISINT","isint"); };
  break;


  case 58: /* func_operator: tkISREAL  */
  if (yyn == 58)
    /* "src/main/java/parser/F.y":238  */
                   { yyval = new parser.TokenValue("ISREAL","isreal"); };
  break;


  case 59: /* func_operator: tkISBOOL  */
  if (yyn == 59)
    /* "src/main/java/parser/F.y":239  */
                   { yyval = new parser.TokenValue("ISBOOL","isbool"); };
  break;


  case 60: /* func_operator: tkISNULL  */
  if (yyn == 60)
    /* "src/main/java/parser/F.y":240  */
                   { yyval = new parser.TokenValue("ISNULL","isnull"); };
  break;


  case 61: /* func_operator: tkISATOM  */
  if (yyn == 61)
    /* "src/main/java/parser/F.y":241  */
                   { yyval = new parser.TokenValue("ISATOM","isatom"); };
  break;


  case 62: /* func_operator: tkISLIST  */
  if (yyn == 62)
    /* "src/main/java/parser/F.y":242  */
                   { yyval = new parser.TokenValue("ISLIST","islist"); };
  break;


  case 63: /* func_operator: tkEVAL  */
  if (yyn == 63)
    /* "src/main/java/parser/F.y":243  */
                   { yyval = new parser.TokenValue("EVAL","eval"); };
  break;


  case 64: /* func_operator: tkIDENT  */
  if (yyn == 64)
    /* "src/main/java/parser/F.y":244  */
                   { yyval = new parser.TokenValue("IDENTIFIER",yystack.valueAt (0)); };
  break;


  case 65: /* argument_list: %empty  */
  if (yyn == 65)
    /* "src/main/java/parser/F.y":249  */
                  { yyval = new ArrayList<Object>(); };
  break;


  case 66: /* argument_list: argument_list element  */
  if (yyn == 66)
    /* "src/main/java/parser/F.y":251  */
        { ((List<Object>)yystack.valueAt (1)).add(yystack.valueAt (0)); yyval = yystack.valueAt (1); };
  break;



/* "src/main/java/parser/FParser.java":997  */

        default: break;
      }

    yystack.pop(yylen);
    yylen = 0;
    /* Shift the result of the reduction.  */
    int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
    yystack.push(yystate, yyval);
    return YYNEWSTATE;
  }




  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse() throws java.io.IOException

  {


    /* Lookahead token kind.  */
    int yychar = YYEMPTY_;
    /* Lookahead symbol kind.  */
    SymbolKind yytoken = null;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;
    YYStack yystack = new YYStack ();
    int label = YYNEWSTATE;



    /* Semantic value of the lookahead.  */
    Object yylval = null;



    yyerrstatus_ = 0;
    yynerrs = 0;

    /* Initialize the stack.  */
    yystack.push (yystate, yylval);



    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
           pushed when we come here.  */
      case YYNEWSTATE:

        /* Accept?  */
        if (yystate == YYFINAL_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyPactValueIsDefault (yyn))
          {
            label = YYDEFAULT;
            break;
          }

        /* Read a lookahead token.  */
        if (yychar == YYEMPTY_)
          {

            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal();

          }

        /* Convert token to internal form.  */
        yytoken = yytranslate_ (yychar);

        if (yytoken == SymbolKind.S_YYerror)
          {
            // The scanner already issued an error message, process directly
            // to error recovery.  But do not keep the error token as
            // lookahead, it is too special and may lead us to an endless
            // loop in error recovery. */
            yychar = Lexer.YYUNDEF;
            yytoken = SymbolKind.S_YYUNDEF;
            label = YYERRLAB1;
          }
        else
          {
            /* If the proper action on seeing token YYTOKEN is to reduce or to
               detect an error, take that action.  */
            yyn += yytoken.getCode();
            if (yyn < 0 || YYLAST_ < yyn || yycheck_[yyn] != yytoken.getCode()) {
              label = YYDEFAULT;
            }

            /* <= 0 means reduce or error.  */
            else if ((yyn = yytable_[yyn]) <= 0)
              {
                if (yyTableValueIsError(yyn)) {
                  label = YYERRLAB;
                } else {
                  yyn = -yyn;
                  label = YYREDUCE;
                }
              }

            else
              {
                /* Shift the lookahead token.  */
                /* Discard the token being shifted.  */
                yychar = YYEMPTY_;

                /* Count tokens shifted since error; after three, turn off error
                   status.  */
                if (yyerrstatus_ > 0)
                  --yyerrstatus_;

                yystate = yyn;
                yystack.push(yystate, yylval);
                label = YYNEWSTATE;
              }
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction(yyn, yystack, yylen);
        yystate = yystack.stateAt(0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs;
            if (yychar == YYEMPTY_)
              yytoken = null;
            yyreportSyntaxError(new Context(this, yystack, yytoken));
          }

        if (yyerrstatus_ == 3)
          {
            /* If just tried and failed to reuse lookahead token after an
               error, discard it.  */

            if (yychar <= Lexer.YYEOF)
              {
                /* Return failure if at end of input.  */
                if (yychar == Lexer.YYEOF)
                  return false;
              }
            else
              yychar = YYEMPTY_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `-------------------------------------------------*/
      case YYERROR:
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt(0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;       /* Each real token shifted decrements this.  */

        // Pop stack until we find a state that shifts the error token.
        for (;;)
          {
            yyn = yypact_[yystate];
            if (!yyPactValueIsDefault (yyn))
              {
                yyn += SymbolKind.S_YYerror.getCode();
                if (0 <= yyn && yyn <= YYLAST_
                    && yycheck_[yyn] == SymbolKind.S_YYerror.getCode())
                  {
                    yyn = yytable_[yyn];
                    if (0 < yyn)
                      break;
                  }
              }

            /* Pop the current state because it cannot handle the
             * error token.  */
            if (yystack.height == 0)
              return false;


            yystack.pop ();
            yystate = yystack.stateAt(0);
          }

        if (label == YYABORT)
          /* Leave the switch.  */
          break;



        /* Shift the error token.  */

        yystate = yyn;
        yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
}




  /**
   * Information needed to get the list of expected tokens and to forge
   * a syntax error diagnostic.
   */
  public static final class Context {
    Context(FParser parser, YYStack stack, SymbolKind token) {
      yyparser = parser;
      yystack = stack;
      yytoken = token;
    }

    private FParser yyparser;
    private YYStack yystack;


    /**
     * The symbol kind of the lookahead token.
     */
    public final SymbolKind getToken() {
      return yytoken;
    }

    private SymbolKind yytoken;
    static final int NTOKENS = FParser.YYNTOKENS_;

    /**
     * Put in YYARG at most YYARGN of the expected tokens given the
     * current YYCTX, and return the number of tokens stored in YYARG.  If
     * YYARG is null, return the number of expected tokens (guaranteed to
     * be less than YYNTOKENS).
     */
    int getExpectedTokens(SymbolKind yyarg[], int yyargn) {
      return getExpectedTokens (yyarg, 0, yyargn);
    }

    int getExpectedTokens(SymbolKind yyarg[], int yyoffset, int yyargn) {
      int yycount = yyoffset;
      int yyn = yypact_[this.yystack.stateAt(0)];
      if (!yyPactValueIsDefault(yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative
             indexes in YYCHECK.  In other words, skip the first
             -YYN actions for this state because they are default
             actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST_ - yyn + 1;
          int yyxend = yychecklim < NTOKENS ? yychecklim : NTOKENS;
          for (int yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck_[yyx + yyn] == yyx && yyx != SymbolKind.S_YYerror.getCode()
                && !yyTableValueIsError(yytable_[yyx + yyn]))
              {
                if (yyarg == null)
                  yycount += 1;
                else if (yycount == yyargn)
                  return 0; // FIXME: this is incorrect.
                else
                  yyarg[yycount++] = SymbolKind.get(yyx);
              }
        }
      if (yyarg != null && yycount == yyoffset && yyoffset < yyargn)
        yyarg[yycount] = null;
      return yycount - yyoffset;
    }
  }




  private int yysyntaxErrorArguments(Context yyctx, SymbolKind[] yyarg, int yyargn) {
    /* There are many possibilities here to consider:
       - If this state is a consistent state with a default action,
         then the only way this function was invoked is if the
         default action is an error action.  In that case, don't
         check for expected tokens because there are none.
       - The only way there can be no lookahead present (in tok) is
         if this state is a consistent state with a default action.
         Thus, detecting the absence of a lookahead is sufficient to
         determine that there is no unexpected or expected token to
         report.  In that case, just report a simple "syntax error".
       - Don't assume there isn't a lookahead just because this
         state is a consistent state with a default action.  There
         might have been a previous inconsistent state, consistent
         state with a non-default action, or user semantic action
         that manipulated yychar.  (However, yychar is currently out
         of scope during semantic actions.)
       - Of course, the expected token list depends on states to
         have correct lookahead information, and it depends on the
         parser not to perform extra reductions after fetching a
         lookahead from the scanner and before detecting a syntax
         error.  Thus, state merging (from LALR or IELR) and default
         reductions corrupt the expected token list.  However, the
         list is correct for canonical LR with one exception: it
         will still contain any token that will not be accepted due
         to an error action in a later state.
    */
    int yycount = 0;
    if (yyctx.getToken() != null)
      {
        if (yyarg != null)
          yyarg[yycount] = yyctx.getToken();
        yycount += 1;
        yycount += yyctx.getExpectedTokens(yyarg, 1, yyargn);
      }
    return yycount;
  }


  /**
   * Build and emit a "syntax error" message in a user-defined way.
   *
   * @param ctx  The context of the error.
   */
  private void yyreportSyntaxError(Context yyctx) {
      if (yyErrorVerbose) {
          final int argmax = 5;
          SymbolKind[] yyarg = new SymbolKind[argmax];
          int yycount = yysyntaxErrorArguments(yyctx, yyarg, argmax);
          String[] yystr = new String[yycount];
          for (int yyi = 0; yyi < yycount; ++yyi) {
              yystr[yyi] = yyarg[yyi].getName();
          }
          String yyformat;
          switch (yycount) {
              default:
              case 0: yyformat = "syntax error"; break;
              case 1: yyformat = "syntax error, unexpected {0}"; break;
              case 2: yyformat = "syntax error, unexpected {0}, expecting {1}"; break;
              case 3: yyformat = "syntax error, unexpected {0}, expecting {1} or {2}"; break;
              case 4: yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3}"; break;
              case 5: yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3} or {4}"; break;
          }
          yyerror(new MessageFormat(yyformat).format(yystr));
      } else {
          yyerror("syntax error");
      }
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yyPactValueIsDefault(int yyvalue) {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code>
   * value indicates a syntax error.
   * @param yyvalue the value to check
   */
  private static boolean yyTableValueIsError(int yyvalue) {
    return yyvalue == yytable_ninf_;
  }

  private static final byte yypact_ninf_ = -42;
  private static final byte yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final byte[] yypact_ = yypact_init();
  private static final byte[] yypact_init()
  {
    return new byte[]
    {
     -42,     1,   -42,    88,   -17,   -42,   -42,   -42,   -42,   -42,
     -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,
     -42,   -42,   -42,   -42,   -42,   -42,   -42,    46,     0,     0,
      24,    24,    46,    46,    46,    25,   -42,   -42,   -42,   -42,
     -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,
     -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,
     -42,     8,   -42,   -42,   -42,    26,    46,    24,    46,   -42,
      46,    46,    37,   -42,   -42,   -42,    15,   -42,    40,    46,
      41,    31,    55,    44,   -42,   -42,   -42,   -42,    52,   -42,
     -42,   -42,    62,   -42,   -42,   -42
    };
  }

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
  private static final byte[] yydefact_ = yydefact_init();
  private static final byte[] yydefact_init()
  {
    return new byte[]
    {
       2,     0,     1,     7,     0,    14,    13,    12,    15,    17,
       3,    11,     4,     9,    10,     5,    18,    20,    21,    22,
      23,    24,    25,    26,    27,    28,    19,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    40,    41,    42,    43,
      44,    45,    46,    47,    48,    49,    50,    51,    52,    57,
      58,    59,    60,    61,    62,    53,    54,    55,    56,    63,
      64,     0,    65,    16,     7,     0,     0,     0,     0,     7,
       0,     0,     0,    38,     6,     8,     0,    29,     0,     0,
       0,     0,     0,     0,    37,    39,    66,    30,     0,    32,
      33,    34,     0,    36,    31,    35
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final byte[] yypgoto_ = yypgoto_init();
  private static final byte[] yypgoto_init()
  {
    return new byte[]
    {
     -42,   -42,   -42,    16,   -41,    -1,   -42,   -26,   -42,   -42,
     -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,   -42,
     -42,   -42
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final byte[] yydefgoto_ = yydefgoto_init();
  private static final byte[] yydefgoto_init()
  {
    return new byte[]
    {
       0,     1,    10,    11,    61,    75,    13,    14,    15,    16,
      17,    18,    19,    20,    21,    22,    23,    24,    25,    26,
      62,    76
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final byte[] yytable_ = yytable_init();
  private static final byte[] yytable_init()
  {
    return new byte[]
    {
      12,     2,    66,    67,     3,     4,     4,     5,     6,     7,
       8,    64,    74,     4,     5,     6,     7,     8,    64,    85,
       4,     5,     6,     7,     8,    63,    65,    64,    81,    73,
      77,    70,    71,    72,    64,    90,     4,     5,     6,     7,
       8,    84,     9,     9,    87,    89,    68,    69,    93,    64,
       9,     4,     5,     6,     7,     8,    94,     9,    64,    91,
       4,     5,     6,     7,     8,    78,    95,    80,     0,    82,
      83,     0,     0,     9,     0,    86,     0,     0,    88,     0,
       0,    92,     0,    79,     0,     0,     0,     0,     9,     0,
       0,     0,     0,    27,     0,     0,     0,     9,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,    41,    42,    43,    44,    45,    46,    47,    48,    49,
      50,    51,    52,    53,    54,    55,    56,    57,    58,    59,
      60
    };
  }

private static final byte[] yycheck_ = yycheck_init();
  private static final byte[] yycheck_init()
  {
    return new byte[]
    {
       1,     0,    28,    29,     3,     5,     5,     6,     7,     8,
       9,     3,     4,     5,     6,     7,     8,     9,     3,     4,
       5,     6,     7,     8,     9,    42,    27,     3,    69,     4,
       4,    32,    33,    34,     3,     4,     5,     6,     7,     8,
       9,     4,    42,    42,     4,     4,    30,    31,     4,     3,
      42,     5,     6,     7,     8,     9,     4,    42,     3,     4,
       5,     6,     7,     8,     9,    66,     4,    68,    -1,    70,
      71,    -1,    -1,    42,    -1,    76,    -1,    -1,    79,    -1,
      -1,    82,    -1,    67,    -1,    -1,    -1,    -1,    42,    -1,
      -1,    -1,    -1,     5,    -1,    -1,    -1,    42,    10,    11,
      12,    13,    14,    15,    16,    17,    18,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,    41,
      42
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final byte[] yystos_ = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,    44,     0,     3,     5,     6,     7,     8,     9,    42,
      45,    46,    48,    49,    50,    51,    52,    53,    54,    55,
      56,    57,    58,    59,    60,    61,    62,     5,    10,    11,
      12,    13,    14,    15,    16,    17,    18,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,    41,
      42,    47,    63,    42,     3,    48,    50,    50,    46,    46,
      48,    48,    48,     4,     4,    48,    64,     4,    48,    46,
      48,    47,    48,    48,     4,     4,    48,     4,    48,     4,
       4,     4,    48,     4,     4,     4
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final byte[] yyr1_ = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    43,    44,    44,    45,    45,    46,    47,    47,    48,
      48,    48,    49,    49,    49,    49,    50,    50,    51,    51,
      52,    52,    52,    52,    52,    52,    52,    52,    52,    53,
      54,    55,    56,    57,    58,    58,    59,    60,    61,    62,
      63,    63,    63,    63,    63,    63,    63,    63,    63,    63,
      63,    63,    63,    63,    63,    63,    63,    63,    63,    63,
      63,    63,    63,    63,    63,    64,    64
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     0,     2,     1,     1,     3,     0,     2,     1,
       1,     1,     1,     1,     1,     1,     2,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     4,
       5,     6,     5,     5,     5,     6,     5,     4,     3,     4,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     0,     2
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 297;
    if (t <= 0)
      return SymbolKind.S_YYEOF;
    else if (t <= code_max)
      return SymbolKind.get(yytranslate_table_[t]);
    else
      return SymbolKind.S_YYUNDEF;
  }
  private static final byte[] yytranslate_table_ = yytranslate_table_init();
  private static final byte[] yytranslate_table_init()
  {
    return new byte[]
    {
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42
    };
  }


  private static final int YYLAST_ = 130;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 2;
  private static final int YYNTOKENS_ = 43;

/* Unqualified %code blocks.  */
/* "src/main/java/parser/F.y":14  */

  /* хранит результат разбора (корневой AST, program) */
  private Object parseResult;
  public Object getParseResult() { return parseResult; }

/* "src/main/java/parser/FParser.java":1643  */

}
