/*
* Pontifical Catholic University of Minas Gerais
* Institue of Exact Sciences and Technology
* Compilers
*
* Authors: Cleber Oliveira, Karen Martins, and Sarah Almeida
* IDs: 486564, 476140, 476181
 */

import java.io.BufferedReader;

/**
 * Processes the tokens generated by the lexical analyzer in order to verify if
 * the source program is generated by the grammar
 */
public class Parser {

    BufferedReader file;
    LexicalAnalyzer lexical;
    SymbolTable table;
    Symbol s;

    /**
     * Default constructor, it initializes the variables that will be used
     * during its execution, creates the instance of the SymbolTable and
     * LexicalAnalyzer
     *
     * @param source BufferedReader containing the information of the source
     * file.
     */
    public Parser(BufferedReader source) {
        try {
            file = source;
            table = new SymbolTable();
            lexical = new LexicalAnalyzer(table);
            s = lexical.getNextLexeme(file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Procedure that implements the initial symbol of the grammar.
     */
    public void S() {
        // S -> {D}
        while (s.getToken() == SymbolTable.INT || s.getToken() == SymbolTable.BOOLEAN || s.getToken() == SymbolTable.BYTE || s.getToken() == SymbolTable.STRING || s.getToken() == SymbolTable.FINAL) {
            D();
        }

        if (s.getToken() == SymbolTable.EOF) {
            System.err.println(lexical.line + ":fim de arquivo nao esperado.");
            System.exit(0);
        }

        // S -> {C}+
        do {
            C();
        } while (s.getToken() == SymbolTable.ID || s.getToken() == SymbolTable.WHILE || s.getToken() == SymbolTable.IF || s.getToken() == SymbolTable.SEMICOLON || s.getToken() == SymbolTable.READLN || s.getToken() == SymbolTable.WRITE || s.getToken() == SymbolTable.WRITELN);
        if (!lexical.EOF) {
            System.err.println(lexical.line + ":token nao esperado [" + s.getLexeme() + "].");
            System.exit(0);
        }
    }

    /**
     * Procedure that implements the declarations.
     */
    private void D() {
        // D -> (INT|BOOLEAN|BYTE|STRING) ID [RECEIVE [MINUS] CONST] {COMMA ID [RECEIVE [MINUS] CONST]} SEMICOLON
        if (s.getToken() == SymbolTable.INT || s.getToken() == SymbolTable.BOOLEAN || s.getToken() == SymbolTable.BYTE || s.getToken() == SymbolTable.STRING) {
            matchToken(s.getToken());
            Symbol id = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U1]
            if (id.getCategory() == Symbol.NO_CATEGORY) {
                table.searchSymbol(id.getLexeme()).setCategory(Symbol.CATEGORY_VARIABLE);
            } else {
                System.err.println(lexical.line + ":identificador ja declarado [" + id.getLexeme() + "].");
                System.exit(0);
            }

            if (s.getToken() == SymbolTable.RECEIVE) {
                matchToken(SymbolTable.RECEIVE);

                if (s.getToken() == SymbolTable.MINUS) {
                    matchToken(SymbolTable.MINUS);
                }

                matchToken(SymbolTable.CONST);
            }

            while (s.getToken() == SymbolTable.COMMA) {
                matchToken(SymbolTable.COMMA);
                id = s;
                matchToken(SymbolTable.ID);
                
                // semantic action [U1]
                if (id.getCategory() == Symbol.NO_CATEGORY) {
                    table.searchSymbol(id.getLexeme()).setCategory(Symbol.CATEGORY_VARIABLE);
                } else {
                    System.err.println(lexical.line + ":identificador ja declarado [" + id.getLexeme() + "].");
                    System.exit(0);
                }

                if (s.getToken() == SymbolTable.RECEIVE) {
                    matchToken(SymbolTable.RECEIVE);

                    if (s.getToken() == SymbolTable.MINUS) {
                        matchToken(SymbolTable.MINUS);
                    }

                    matchToken(SymbolTable.CONST);
                }
            }

            matchToken(SymbolTable.SEMICOLON);
        } // D -> FINAL ID RECEIVE [MINUS] CONST SEMICOLON
        else {
            matchToken(SymbolTable.FINAL);
            Symbol id = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U2]
            if (id.getCategory() == Symbol.NO_CATEGORY) {
                table.searchSymbol(id.getLexeme()).setCategory(Symbol.CATEGORY_CONSTANT);
            } else {
                System.err.println(lexical.line + ":identificador ja declarado [" + id.getLexeme() + "].");
                System.exit(0);
            }
            
            matchToken(SymbolTable.RECEIVE);

            if (s.getToken() == SymbolTable.MINUS) {
                matchToken(SymbolTable.MINUS);
            }

            matchToken(SymbolTable.CONST);
            matchToken(SymbolTable.SEMICOLON);
        }
    }

    /**
     * Procedure that implements the commands.
     */
    private void C() {
        Symbol id;
        
        // C -> ID RECEIVE E SEMICOLON
        if (s.getToken() == SymbolTable.ID) {
            id = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U3]
            if (id.getCategory() == Symbol.NO_CATEGORY) {
                System.err.println(lexical.line + ":identificador nao declarado [" + id.getLexeme() + "].");
                System.exit(0);
            } 
            
            matchToken(SymbolTable.RECEIVE);
            E();
            matchToken(SymbolTable.SEMICOLON);
        } // C -> WHILE OPPAR E CLPAR (BEGIN {C} ENDWHILE | C)
        else if (s.getToken() == SymbolTable.WHILE) {
            matchToken(SymbolTable.WHILE);
            matchToken(SymbolTable.OPPAR);
            E();
            matchToken(SymbolTable.CLPAR);

            if (s.getToken() == SymbolTable.BEGIN) {
                matchToken(SymbolTable.BEGIN);

                while (s.getToken() == SymbolTable.ID || s.getToken() == SymbolTable.WHILE || s.getToken() == SymbolTable.IF || s.getToken() == SymbolTable.SEMICOLON || s.getToken() == SymbolTable.READLN || s.getToken() == SymbolTable.WRITE || s.getToken() == SymbolTable.WRITELN) {
                    C();
                }

                matchToken(SymbolTable.ENDWHILE);
            } else {
                C();
            }
        } // C -> IF OPPAR E CLPAR (BEGIN {C} ENDIF [ELSE (BEGIN {C} ENDELSE | C)] | C [ELSE (BEGIN {C} ENDELSE | C)])
        else if (s.getToken() == SymbolTable.IF) {
            matchToken(SymbolTable.IF);
            matchToken(SymbolTable.OPPAR);
            E();
            matchToken(SymbolTable.CLPAR);

            if (s.getToken() == SymbolTable.BEGIN) {
                matchToken(SymbolTable.BEGIN);

                while (s.getToken() == SymbolTable.ID || s.getToken() == SymbolTable.WHILE || s.getToken() == SymbolTable.IF || s.getToken() == SymbolTable.SEMICOLON || s.getToken() == SymbolTable.READLN || s.getToken() == SymbolTable.WRITE || s.getToken() == SymbolTable.WRITELN) {
                    C();
                }

                matchToken(SymbolTable.ENDIF);

                if (s.getToken() == SymbolTable.ELSE) {
                    matchToken(SymbolTable.ELSE);

                    if (s.getToken() == SymbolTable.BEGIN) {
                        matchToken(SymbolTable.BEGIN);

                        while (s.getToken() == SymbolTable.ID || s.getToken() == SymbolTable.WHILE || s.getToken() == SymbolTable.IF || s.getToken() == SymbolTable.SEMICOLON || s.getToken() == SymbolTable.READLN || s.getToken() == SymbolTable.WRITE || s.getToken() == SymbolTable.WRITELN) {
                            C();
                        }

                        matchToken(SymbolTable.ENDELSE);
                    } else {
                        C();
                    }
                }
            } else {
                C();

                if (s.getToken() == SymbolTable.ELSE) {
                    matchToken(SymbolTable.ELSE);

                    if (s.getToken() == SymbolTable.BEGIN) {
                        matchToken(SymbolTable.BEGIN);

                        while (s.getToken() == SymbolTable.ID || s.getToken() == SymbolTable.WHILE || s.getToken() == SymbolTable.IF || s.getToken() == SymbolTable.SEMICOLON || s.getToken() == SymbolTable.READLN || s.getToken() == SymbolTable.WRITE || s.getToken() == SymbolTable.WRITELN) {
                            C();
                        }

                        matchToken(SymbolTable.ENDELSE);
                    } else {
                        C();
                    }
                }
            }
        } // C -> SEMICOLON
        else if (s.getToken() == SymbolTable.SEMICOLON) {
            matchToken(SymbolTable.SEMICOLON);
        } // C -> READLN OPPAR ID CLPAR SEMICOLON
        else if (s.getToken() == SymbolTable.READLN) {
            matchToken(SymbolTable.READLN);
            matchToken(SymbolTable.OPPAR);
            id = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U3]
            if (id.getCategory() == Symbol.NO_CATEGORY) {
                System.err.println(lexical.line + ":identificador nao declarado [" + id.getLexeme() + "].");
                System.exit(0);
            } 
            
            matchToken(SymbolTable.CLPAR);
            matchToken(SymbolTable.SEMICOLON);
        } // C -> (WRITE | WRITELN) OPPAR E {COMMA E} CLPAR SEMICOLON
        else {
            if (s.getToken() == SymbolTable.WRITE) {
                matchToken(SymbolTable.WRITE);
            } else {
                matchToken(SymbolTable.WRITELN);
            }

            matchToken(SymbolTable.OPPAR);
            E();
            while (s.getToken() == SymbolTable.COMMA) {
                matchToken(SymbolTable.COMMA);
                E();
            }

            matchToken(SymbolTable.CLPAR);
            matchToken(SymbolTable.SEMICOLON);
        }
    }

    /**
     * Procedure that implements the expressions.
     */
    private void E() {
        // E -> X [(LESSTHAN | MORETHAN | LESSEQUAL | MOREEQUAL | DIFFERENT | EQUALS) X]
        X();

        if (s.getToken() == SymbolTable.LESSTHAN || s.getToken() == SymbolTable.MORETHAN || s.getToken() == SymbolTable.LESSEQUAL || s.getToken() == SymbolTable.MOREEQUAL || s.getToken() == SymbolTable.DIFFERENT || s.getToken() == SymbolTable.EQUALS) {
            matchToken(s.getToken());
            X();
        }
    }

    /**
     * Procedure that implements the simple expressions.
     */
    private void X() {
        // X -> [PLUS | MINUS] T {(PLUS | MINUS | OR) T}
        if (s.getToken() == SymbolTable.PLUS || s.getToken() == SymbolTable.MINUS) {
            matchToken(s.getToken());
        }

        T();

        while (s.getToken() == SymbolTable.PLUS || s.getToken() == SymbolTable.MINUS || s.getToken() == SymbolTable.OR) {
            matchToken(s.getToken());
            T();
        }
    }

    /**
     * Procedure that implements the terms.
     */
    private void T() {
        // T -> F {(TIMES | DIVIDE | AND) F}
        F();

        while (s.getToken() == SymbolTable.TIMES || s.getToken() == SymbolTable.DIVIDE || s.getToken() == SymbolTable.AND) {
            matchToken(s.getToken());
            F();
        }
    }

    /**
     * Procedure that implements the factors.
     */
    private void F() {
        Symbol id;
        
        // F -> OPPAR E CLPAR
        if (s.getToken() == SymbolTable.OPPAR) {
            matchToken(SymbolTable.OPPAR);
            E();
            matchToken(SymbolTable.CLPAR);
        } // F -> ID
        else if (s.getToken() == SymbolTable.ID) {
            id = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U3]
            if (id.getCategory() == Symbol.NO_CATEGORY) {
                System.err.println(lexical.line + ":identificador nao declarado [" + id.getLexeme() + "].");
                System.exit(0);
            } 
        } // F -> CONST
        else if (s.getToken() == SymbolTable.CONST) {
            matchToken(SymbolTable.CONST);
        } // F -> NOT F
        else {
            matchToken(SymbolTable.NOT);
            F();
        }
    }

    /**
     * Compares if the received token equals to the expected. If so, it calls
     * the lexical analyzer and reads the next token; otherwise, it will stop
     * the execution with an error
     *
     * @param token expected token
     */
    private void matchToken(byte token) {
        try {
            if (!lexical.EOF) {
                if (s.getToken() == token) {
                    s = lexical.getNextLexeme(file);
                } else if (s == null) {
                    System.err.println(lexical.line + ":fim de arquivo nao esperado.");
                    System.exit(0);
                } else {
                    System.err.println(lexical.line + ":token nao esperado [" + s.getLexeme() + "].");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.err.println("matchToken: " + e.getMessage());
        }
    }

}
