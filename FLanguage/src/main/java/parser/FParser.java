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
    S_list(45),                    /* list  */
    S_list_elements(46),           /* list_elements  */
    S_literal(47),                 /* literal  */
    S_atom(48),                    /* atom  */
    S_element(49),                 /* element  */
    S_special_form(50),            /* special_form  */
    S_quote_form(51),              /* quote_form  */
    S_setq_form(52),               /* setq_form  */
    S_func_form(53),               /* func_form  */
    S_lambda_form(54),             /* lambda_form  */
    S_prog_form(55),               /* prog_form  */
    S_cond_form(56),               /* cond_form  */
    S_while_form(57),              /* while_form  */
    S_return_form(58),             /* return_form  */
    S_break_form(59),              /* break_form  */
    S_function(60),                /* function  */
    S_arithmetic_function_call(61), /* arithmetic_function_call  */
    S_operation_on_list_call(62),  /* operation_on_list_call  */
    S_comparision_call(63),        /* comparision_call  */
    S_predicate_call(64),          /* predicate_call  */
    S_logical_operation_call(65),  /* logical_operation_call  */
    S_evaluation_call(66),         /* evaluation_call  */
    S_generic_call(67),            /* generic_call  */
    S_argument_list(68);           /* argument_list  */


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
      SymbolKind.S_list,
      SymbolKind.S_list_elements,
      SymbolKind.S_literal,
      SymbolKind.S_atom,
      SymbolKind.S_element,
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
      SymbolKind.S_function,
      SymbolKind.S_arithmetic_function_call,
      SymbolKind.S_operation_on_list_call,
      SymbolKind.S_comparision_call,
      SymbolKind.S_predicate_call,
      SymbolKind.S_logical_operation_call,
      SymbolKind.S_evaluation_call,
      SymbolKind.S_generic_call,
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
  "$accept", "program", "list", "list_elements", "literal", "atom",
  "element", "special_form", "quote_form", "setq_form", "func_form",
  "lambda_form", "prog_form", "cond_form", "while_form", "return_form",
  "break_form", "function", "arithmetic_function_call",
  "operation_on_list_call", "comparision_call", "predicate_call",
  "logical_operation_call", "evaluation_call", "generic_call",
  "argument_list", null
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


  case 3: /* program: program element  */
  if (yyn == 3)
    /* "src/main/java/parser/F.y":29  */
                       { ((List<Object>) yystack.valueAt (1)).add(yystack.valueAt (0)); yyval = yystack.valueAt (1); parseResult = yyval; };
  break;


  case 4: /* list: tkLPAREN list_elements tkRPAREN  */
  if (yyn == 4)
    /* "src/main/java/parser/F.y":33  */
                                       { yyval = yystack.valueAt (1); };
  break;


  case 5: /* list_elements: %empty  */
  if (yyn == 5)
    /* "src/main/java/parser/F.y":38  */
        { yyval = new ArrayList<Object>(); };
  break;


  case 6: /* list_elements: list_elements element  */
  if (yyn == 6)
    /* "src/main/java/parser/F.y":40  */
        { ((List<Object>) yystack.valueAt (1)).add(yystack.valueAt (0)); yyval = yystack.valueAt (1); };
  break;


  case 7: /* literal: tkINT  */
  if (yyn == 7)
    /* "src/main/java/parser/F.y":44  */
               { yyval = new parser.TokenValue("INT", yystack.valueAt (0)); };
  break;


  case 8: /* literal: tkREAL  */
  if (yyn == 8)
    /* "src/main/java/parser/F.y":45  */
               { yyval = new parser.TokenValue("REAL", yystack.valueAt (0)); };
  break;


  case 9: /* literal: tkBOOL  */
  if (yyn == 9)
    /* "src/main/java/parser/F.y":46  */
               { yyval = new parser.TokenValue("BOOL", yystack.valueAt (0)); };
  break;


  case 10: /* literal: tkNULL  */
  if (yyn == 10)
    /* "src/main/java/parser/F.y":47  */
               { yyval = new parser.TokenValue("NULL", null); };
  break;


  case 11: /* atom: tkSETQ  */
  if (yyn == 11)
    /* "src/main/java/parser/F.y":51  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 12: /* atom: tkFUNC  */
  if (yyn == 12)
    /* "src/main/java/parser/F.y":52  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 13: /* atom: tkLAMBDA  */
  if (yyn == 13)
    /* "src/main/java/parser/F.y":53  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 14: /* atom: tkPROG  */
  if (yyn == 14)
    /* "src/main/java/parser/F.y":54  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 15: /* atom: tkCOND  */
  if (yyn == 15)
    /* "src/main/java/parser/F.y":55  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 16: /* atom: tkWHILE  */
  if (yyn == 16)
    /* "src/main/java/parser/F.y":56  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 17: /* atom: tkRETURN  */
  if (yyn == 17)
    /* "src/main/java/parser/F.y":57  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 18: /* atom: tkBREAK  */
  if (yyn == 18)
    /* "src/main/java/parser/F.y":58  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 19: /* atom: tkPLUS  */
  if (yyn == 19)
    /* "src/main/java/parser/F.y":60  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 20: /* atom: tkMINUS  */
  if (yyn == 20)
    /* "src/main/java/parser/F.y":61  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 21: /* atom: tkTIMES  */
  if (yyn == 21)
    /* "src/main/java/parser/F.y":62  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 22: /* atom: tkDIVIDE  */
  if (yyn == 22)
    /* "src/main/java/parser/F.y":63  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 23: /* atom: tkHEAD  */
  if (yyn == 23)
    /* "src/main/java/parser/F.y":65  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 24: /* atom: tkTAIL  */
  if (yyn == 24)
    /* "src/main/java/parser/F.y":66  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 25: /* atom: tkCONS  */
  if (yyn == 25)
    /* "src/main/java/parser/F.y":67  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 26: /* atom: tkEQUAL  */
  if (yyn == 26)
    /* "src/main/java/parser/F.y":69  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 27: /* atom: tkNONEQUAL  */
  if (yyn == 27)
    /* "src/main/java/parser/F.y":70  */
                    { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 28: /* atom: tkLESS  */
  if (yyn == 28)
    /* "src/main/java/parser/F.y":71  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 29: /* atom: tkLESSEQ  */
  if (yyn == 29)
    /* "src/main/java/parser/F.y":72  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 30: /* atom: tkGREATER  */
  if (yyn == 30)
    /* "src/main/java/parser/F.y":73  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 31: /* atom: tkGREATEREQ  */
  if (yyn == 31)
    /* "src/main/java/parser/F.y":74  */
                    { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 32: /* atom: tkISINT  */
  if (yyn == 32)
    /* "src/main/java/parser/F.y":76  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 33: /* atom: tkISREAL  */
  if (yyn == 33)
    /* "src/main/java/parser/F.y":77  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 34: /* atom: tkISBOOL  */
  if (yyn == 34)
    /* "src/main/java/parser/F.y":78  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 35: /* atom: tkISNULL  */
  if (yyn == 35)
    /* "src/main/java/parser/F.y":79  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 36: /* atom: tkISATOM  */
  if (yyn == 36)
    /* "src/main/java/parser/F.y":80  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 37: /* atom: tkISLIST  */
  if (yyn == 37)
    /* "src/main/java/parser/F.y":81  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 38: /* atom: tkAND  */
  if (yyn == 38)
    /* "src/main/java/parser/F.y":83  */
            { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 39: /* atom: tkOR  */
  if (yyn == 39)
    /* "src/main/java/parser/F.y":84  */
            { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 40: /* atom: tkXOR  */
  if (yyn == 40)
    /* "src/main/java/parser/F.y":85  */
            { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 41: /* atom: tkNOT  */
  if (yyn == 41)
    /* "src/main/java/parser/F.y":86  */
            { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 42: /* atom: tkEVAL  */
  if (yyn == 42)
    /* "src/main/java/parser/F.y":87  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 43: /* atom: tkIDENT  */
  if (yyn == 43)
    /* "src/main/java/parser/F.y":88  */
                { yyval = new parser.TokenValue("IDENTIFIER", yystack.valueAt (0)); };
  break;


  case 44: /* element: list  */
  if (yyn == 44)
    /* "src/main/java/parser/F.y":93  */
               { yyval = yystack.valueAt (0); };
  break;


  case 45: /* element: special_form  */
  if (yyn == 45)
    /* "src/main/java/parser/F.y":94  */
                   { yyval = yystack.valueAt (0); };
  break;


  case 46: /* element: function  */
  if (yyn == 46)
    /* "src/main/java/parser/F.y":95  */
               { yyval = yystack.valueAt (0); };
  break;


  case 47: /* element: literal  */
  if (yyn == 47)
    /* "src/main/java/parser/F.y":96  */
               { yyval = yystack.valueAt (0); };
  break;


  case 48: /* element: atom  */
  if (yyn == 48)
    /* "src/main/java/parser/F.y":97  */
               { yyval = yystack.valueAt (0); };
  break;


  case 58: /* quote_form: tkQUOTE list  */
  if (yyn == 58)
    /* "src/main/java/parser/F.y":114  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("QUOTE", "quote"));
            form.add(yystack.valueAt (0));
            yyval = form;
        };
  break;


  case 59: /* quote_form: tkQUOTE atom  */
  if (yyn == 59)
    /* "src/main/java/parser/F.y":121  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("QUOTE", "quote"));
            form.add(yystack.valueAt (0));
            yyval = form;
        };
  break;


  case 60: /* quote_form: tkQUOTE literal  */
  if (yyn == 60)
    /* "src/main/java/parser/F.y":128  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("QUOTE", "quote"));
            form.add(yystack.valueAt (0));
            yyval = form;
        };
  break;


  case 61: /* quote_form: tkLPAREN tkQUOTE element tkRPAREN  */
  if (yyn == 61)
    /* "src/main/java/parser/F.y":135  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("QUOTE", "quote"));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 62: /* setq_form: tkLPAREN tkSETQ atom element tkRPAREN  */
  if (yyn == 62)
    /* "src/main/java/parser/F.y":148  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("SETQ", "setq"));
            form.add(yystack.valueAt (2));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 63: /* func_form: tkLPAREN tkFUNC atom list element tkRPAREN  */
  if (yyn == 63)
    /* "src/main/java/parser/F.y":159  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("FUNC", "func"));
            form.add(yystack.valueAt (3));
            form.add(yystack.valueAt (2));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 64: /* lambda_form: tkLPAREN tkLAMBDA list element tkRPAREN  */
  if (yyn == 64)
    /* "src/main/java/parser/F.y":171  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("LAMBDA", "lambda"));
            form.add(yystack.valueAt (2));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 65: /* prog_form: tkLPAREN tkPROG list list_elements tkRPAREN  */
  if (yyn == 65)
    /* "src/main/java/parser/F.y":182  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("PROG", "prog"));
            form.add(yystack.valueAt (2));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 66: /* cond_form: tkLPAREN tkCOND element element tkRPAREN  */
  if (yyn == 66)
    /* "src/main/java/parser/F.y":193  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("COND", "cond"));
            form.add(yystack.valueAt (2));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 67: /* cond_form: tkLPAREN tkCOND element element element tkRPAREN  */
  if (yyn == 67)
    /* "src/main/java/parser/F.y":201  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("COND", "cond"));
            form.add(yystack.valueAt (3));
            form.add(yystack.valueAt (2));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 68: /* while_form: tkLPAREN tkWHILE element element tkRPAREN  */
  if (yyn == 68)
    /* "src/main/java/parser/F.y":213  */
        {
            List<Object> form= new ArrayList<Object>();
            form.add(new parser.TokenValue("WHILE", "while"));
            form.add(yystack.valueAt (2));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 69: /* return_form: tkLPAREN tkRETURN element tkRPAREN  */
  if (yyn == 69)
    /* "src/main/java/parser/F.y":224  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("RETURN", "return"));
            form.add(yystack.valueAt (1));
            yyval = form;
        };
  break;


  case 70: /* break_form: tkLPAREN tkBREAK tkRPAREN  */
  if (yyn == 70)
    /* "src/main/java/parser/F.y":234  */
        {
            List<Object> form = new ArrayList<Object>();
            form.add(new parser.TokenValue("BREAK", "break"));
            yyval = form;
        };
  break;


  case 78: /* arithmetic_function_call: tkLPAREN tkPLUS element element tkRPAREN  */
  if (yyn == 78)
    /* "src/main/java/parser/F.y":253  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("PLUS", "plus"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 79: /* arithmetic_function_call: tkLPAREN tkMINUS element element tkRPAREN  */
  if (yyn == 79)
    /* "src/main/java/parser/F.y":261  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("MINUS", "minus"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 80: /* arithmetic_function_call: tkLPAREN tkTIMES element element tkRPAREN  */
  if (yyn == 80)
    /* "src/main/java/parser/F.y":269  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("TIMES", "times"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 81: /* arithmetic_function_call: tkLPAREN tkDIVIDE element element tkRPAREN  */
  if (yyn == 81)
    /* "src/main/java/parser/F.y":277  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("DIVIDE", "divide"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 82: /* operation_on_list_call: tkLPAREN tkHEAD element tkRPAREN  */
  if (yyn == 82)
    /* "src/main/java/parser/F.y":288  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("HEAD", "head"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 83: /* operation_on_list_call: tkLPAREN tkTAIL element tkRPAREN  */
  if (yyn == 83)
    /* "src/main/java/parser/F.y":295  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("TAIL", "tail"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 84: /* operation_on_list_call: tkLPAREN tkCONS element element tkRPAREN  */
  if (yyn == 84)
    /* "src/main/java/parser/F.y":302  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("CONS", "cons"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 85: /* comparision_call: tkLPAREN tkEQUAL element element tkRPAREN  */
  if (yyn == 85)
    /* "src/main/java/parser/F.y":313  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("EQUAL", "equal"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 86: /* comparision_call: tkLPAREN tkNONEQUAL element element tkRPAREN  */
  if (yyn == 86)
    /* "src/main/java/parser/F.y":321  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("NONEQUAL", "nonequal"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 87: /* comparision_call: tkLPAREN tkLESS element element tkRPAREN  */
  if (yyn == 87)
    /* "src/main/java/parser/F.y":329  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("LESS", "less"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 88: /* comparision_call: tkLPAREN tkLESSEQ element element tkRPAREN  */
  if (yyn == 88)
    /* "src/main/java/parser/F.y":337  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("LESSEQ", "lesseq"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 89: /* comparision_call: tkLPAREN tkGREATER element element tkRPAREN  */
  if (yyn == 89)
    /* "src/main/java/parser/F.y":345  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("GREATER", "greater"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 90: /* comparision_call: tkLPAREN tkGREATEREQ element element tkRPAREN  */
  if (yyn == 90)
    /* "src/main/java/parser/F.y":353  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("GREATEREQ", "greatereq"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 91: /* predicate_call: tkLPAREN tkISINT element tkRPAREN  */
  if (yyn == 91)
    /* "src/main/java/parser/F.y":364  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISINT", "isint"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 92: /* predicate_call: tkLPAREN tkISREAL element tkRPAREN  */
  if (yyn == 92)
    /* "src/main/java/parser/F.y":371  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISREAL", "isreal"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 93: /* predicate_call: tkLPAREN tkISBOOL element tkRPAREN  */
  if (yyn == 93)
    /* "src/main/java/parser/F.y":378  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISBOOL", "isbool"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 94: /* predicate_call: tkLPAREN tkISNULL element tkRPAREN  */
  if (yyn == 94)
    /* "src/main/java/parser/F.y":385  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISNULL", "isnull"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 95: /* predicate_call: tkLPAREN tkISATOM element tkRPAREN  */
  if (yyn == 95)
    /* "src/main/java/parser/F.y":392  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISATOM", "isatom"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 96: /* predicate_call: tkLPAREN tkISLIST element tkRPAREN  */
  if (yyn == 96)
    /* "src/main/java/parser/F.y":399  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("ISLIST", "islist"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 97: /* logical_operation_call: tkLPAREN tkAND element element tkRPAREN  */
  if (yyn == 97)
    /* "src/main/java/parser/F.y":409  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("AND", "and"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 98: /* logical_operation_call: tkLPAREN tkOR element element tkRPAREN  */
  if (yyn == 98)
    /* "src/main/java/parser/F.y":417  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("OR", "or"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 99: /* logical_operation_call: tkLPAREN tkXOR element element tkRPAREN  */
  if (yyn == 99)
    /* "src/main/java/parser/F.y":425  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("XOR", "xor"));
            call.add(yystack.valueAt (2));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 100: /* logical_operation_call: tkLPAREN tkNOT element tkRPAREN  */
  if (yyn == 100)
    /* "src/main/java/parser/F.y":433  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("NOT", "not"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 101: /* evaluation_call: tkLPAREN tkEVAL element tkRPAREN  */
  if (yyn == 101)
    /* "src/main/java/parser/F.y":443  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(new parser.TokenValue("EVAL", "eval"));
            call.add(yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 102: /* generic_call: tkLPAREN atom argument_list tkRPAREN  */
  if (yyn == 102)
    /* "src/main/java/parser/F.y":453  */
        {
            List<Object> call = new ArrayList<Object>();
            call.add(yystack.valueAt (2));
            call.addAll((List<Object>) yystack.valueAt (1));
            yyval = call;
        };
  break;


  case 103: /* argument_list: %empty  */
  if (yyn == 103)
    /* "src/main/java/parser/F.y":462  */
                                     { yyval = new ArrayList<Object>(); };
  break;


  case 104: /* argument_list: argument_list element  */
  if (yyn == 104)
    /* "src/main/java/parser/F.y":463  */
                                     { ((List<Object>) yystack.valueAt (1)).add(yystack.valueAt (0)); yyval = yystack.valueAt (1); };
  break;



/* "src/main/java/parser/FParser.java":1422  */

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

  private static final short yypact_ninf_ = -4;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      -4,     3,    -4,   379,   341,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,   301,   412,   412,     4,     4,   301,
     301,   301,    -2,   301,   301,   301,   301,   301,   301,   301,
     301,   301,   301,   301,   301,   301,   301,   301,   301,   301,
     301,   301,   301,   301,   301,   301,   301,   141,    -4,    -4,
      -4,    -4,    -4,    42,   301,     4,   301,    -4,   301,   301,
      43,    -4,   301,   301,   301,   301,    44,    45,   301,   301,
     301,   301,   301,   301,   301,    46,    47,    48,    49,    50,
      51,   301,   301,   301,    52,    53,    -4,    -4,   181,    -4,
      54,   301,    55,   221,   261,    56,    -4,    57,    58,    67,
      92,    -4,    -4,    93,    94,    95,    96,    97,   100,   102,
      -4,    -4,    -4,    -4,    -4,    -4,   105,   106,   111,    -4,
      -4,    -4,    -4,    -4,   112,    -4,    -4,    -4,   120,    -4,
      -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4
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
       2,     0,     1,     5,     0,     9,     8,     7,    10,    11,
      12,    13,    14,    15,    16,    17,    18,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,    41,
      42,    43,    44,    47,    48,     3,    45,    49,    50,    51,
      52,    53,    54,    55,    56,    57,    46,    71,    72,    73,
      74,    75,    76,    77,     0,    11,    12,    13,    14,    15,
      16,    17,    18,    19,    20,    21,    22,    23,    24,    25,
      26,    27,    28,    29,    30,    31,    32,    33,    34,    35,
      36,    37,    38,    39,    40,    41,    42,     0,   103,     5,
      58,    60,    59,     0,     0,     0,     0,     5,     0,     0,
       0,    70,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     4,     6,     0,    61,
       0,     0,     0,     0,     0,     0,    69,     0,     0,     0,
       0,    82,    83,     0,     0,     0,     0,     0,     0,     0,
      91,    92,    93,    94,    95,    96,     0,     0,     0,   100,
     101,   102,   104,    62,     0,    64,    65,    66,     0,    68,
      78,    79,    80,    81,    84,    85,    86,    87,    88,    89,
      90,    97,    98,    99,    63,    67
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final byte[] yypgoto_ = yypgoto_init();
  private static final byte[] yypgoto_init()
  {
    return new byte[]
    {
      -4,    -4,    -3,    18,   122,     1,    -1,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,    -4,
      -4,    -4,    -4,    -4,    -4,    -4
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     1,    42,    97,    43,    44,   137,    46,    47,    48,
      49,    50,    51,    52,    53,    54,    55,    56,    57,    58,
      59,    60,    61,    62,    63,   138
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final short[] yytable_ = yytable_init();
  private static final short[] yytable_init()
  {
    return new short[]
    {
      45,   100,   111,     2,    98,   102,     3,    99,     4,     5,
       6,     7,     8,     9,    10,    11,    12,    13,    14,    15,
      16,    17,    18,    19,    20,    21,    22,    23,    24,    25,
      26,    27,    28,    29,    30,    31,    32,    33,    34,    35,
      36,    37,    38,    39,    40,    41,   139,   146,   151,   152,
     160,   161,   162,   163,   164,   165,   169,   170,   173,   175,
     179,   180,   181,   103,   106,   107,   104,   105,   108,   109,
     110,   182,   112,   113,   114,   115,   116,   117,   118,   119,
     120,   121,   122,   123,   124,   125,   126,   127,   128,   129,
     130,   131,   132,   133,   134,   135,   183,   184,   185,   186,
     187,   188,   141,   140,   189,   142,   190,   144,   145,   191,
     192,   147,   148,   149,   150,   193,   194,   153,   154,   155,
     156,   157,   158,   159,   195,   143,   101,     0,     0,     0,
     166,   167,   168,     0,     0,     0,     0,   172,     0,     0,
     174,     0,     0,   178,     3,   136,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,    41,     3,   171,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,    41,     3,   176,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,    41,     3,   177,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,    41,     3,     0,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,    41,    99,     0,     0,     5,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,    41,    64,     0,     0,     0,     0,    65,
      66,    67,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      86,    87,    88,    89,    90,    91,    92,    93,    94,    95,
      96,    41,     9,    10,    11,    12,    13,    14,    15,    16,
      17,    18,    19,    20,    21,    22,    23,    24,    25,    26,
      27,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,    41
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
       1,     4,     4,     0,     3,     4,     3,     3,     5,     6,
       7,     8,     9,    10,    11,    12,    13,    14,    15,    16,
      17,    18,    19,    20,    21,    22,    23,    24,    25,    26,
      27,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,    41,    42,     4,     4,     4,     4,
       4,     4,     4,     4,     4,     4,     4,     4,     4,     4,
       4,     4,     4,    64,    67,    68,    65,    66,    69,    70,
      71,     4,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    86,    87,    88,    89,    90,
      91,    92,    93,    94,    95,    96,     4,     4,     4,     4,
       4,     4,   105,   104,     4,   106,     4,   108,   109,     4,
       4,   112,   113,   114,   115,     4,     4,   118,   119,   120,
     121,   122,   123,   124,     4,   107,     4,    -1,    -1,    -1,
     131,   132,   133,    -1,    -1,    -1,    -1,   138,    -1,    -1,
     141,    -1,    -1,   144,     3,     4,     5,     6,     7,     8,
       9,    10,    11,    12,    13,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,     3,     4,     5,     6,     7,     8,
       9,    10,    11,    12,    13,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,     3,     4,     5,     6,     7,     8,
       9,    10,    11,    12,    13,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,     3,     4,     5,     6,     7,     8,
       9,    10,    11,    12,    13,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,     3,    -1,     5,     6,     7,     8,
       9,    10,    11,    12,    13,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,     3,    -1,    -1,     6,     7,     8,
       9,    10,    11,    12,    13,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,     5,    -1,    -1,    -1,    -1,    10,
      11,    12,    13,    14,    15,    16,    17,    18,    19,    20,
      21,    22,    23,    24,    25,    26,    27,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
      41,    42,    10,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,    41,    42
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final byte[] yystos_ = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,    44,     0,     3,     5,     6,     7,     8,     9,    10,
      11,    12,    13,    14,    15,    16,    17,    18,    19,    20,
      21,    22,    23,    24,    25,    26,    27,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
      41,    42,    45,    47,    48,    49,    50,    51,    52,    53,
      54,    55,    56,    57,    58,    59,    60,    61,    62,    63,
      64,    65,    66,    67,     5,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    46,    48,     3,
      45,    47,    48,    49,    48,    48,    45,    45,    49,    49,
      49,     4,    49,    49,    49,    49,    49,    49,    49,    49,
      49,    49,    49,    49,    49,    49,    49,    49,    49,    49,
      49,    49,    49,    49,    49,    49,     4,    49,    68,     4,
      49,    45,    49,    46,    49,    49,     4,    49,    49,    49,
      49,     4,     4,    49,    49,    49,    49,    49,    49,    49,
       4,     4,     4,     4,     4,     4,    49,    49,    49,     4,
       4,     4,    49,     4,    49,     4,     4,     4,    49,     4,
       4,     4,     4,     4,     4,     4,     4,     4,     4,     4,
       4,     4,     4,     4,     4,     4
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final byte[] yyr1_ = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    43,    44,    44,    45,    46,    46,    47,    47,    47,
      47,    48,    48,    48,    48,    48,    48,    48,    48,    48,
      48,    48,    48,    48,    48,    48,    48,    48,    48,    48,
      48,    48,    48,    48,    48,    48,    48,    48,    48,    48,
      48,    48,    48,    48,    49,    49,    49,    49,    49,    50,
      50,    50,    50,    50,    50,    50,    50,    50,    51,    51,
      51,    51,    52,    53,    54,    55,    56,    56,    57,    58,
      59,    60,    60,    60,    60,    60,    60,    60,    61,    61,
      61,    61,    62,    62,    62,    63,    63,    63,    63,    63,
      63,    64,    64,    64,    64,    64,    64,    65,    65,    65,
      65,    66,    67,    68,    68
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     0,     2,     3,     0,     2,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     2,
       2,     4,     5,     6,     5,     5,     5,     6,     5,     4,
       3,     1,     1,     1,     1,     1,     1,     1,     5,     5,
       5,     5,     4,     4,     5,     5,     5,     5,     5,     5,
       5,     4,     4,     4,     4,     4,     4,     5,     5,     5,
       4,     4,     4,     0,     2
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


  private static final int YYLAST_ = 454;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 2;
  private static final int YYNTOKENS_ = 43;

/* Unqualified %code blocks.  */
/* "src/main/java/parser/F.y":14  */

  /* хранит результат разбора (корневой AST, program) */
  private Object parseResult;
  public Object getParseResult() { return parseResult; }

/* "src/main/java/parser/FParser.java":2170  */

}
