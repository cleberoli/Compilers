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
        Symbol _Did, _Dconst;
        AuxiliarySymbol _D = new AuxiliarySymbol();
        boolean _Dminus, _Dinitialized;
        // D -> (INT|BOOLEAN|BYTE|STRING) ID [RECEIVE [MINUS] CONST] {COMMA ID [RECEIVE [MINUS] CONST]} SEMICOLON
        if (s.getToken() == SymbolTable.INT || s.getToken() == SymbolTable.BOOLEAN || s.getToken() == SymbolTable.BYTE || s.getToken() == SymbolTable.STRING) {
            
            // semantic action [T1]
            if (s.getToken() == SymbolTable.INT) {
                _D.setType(Symbol.TYPE_INTEGER);
            } 
            // semantic action [T2]
            else if (s.getToken() == SymbolTable.BOOLEAN) {
                _D.setType(Symbol.TYPE_LOGICAL);
            }
            // semantic action [T3]
            else if (s.getToken() == SymbolTable.BYTE) {
                _D.setType(Symbol.TYPE_BYTE);
            }
            // semantic action [T1]
            else {
                _D.setType(Symbol.TYPE_STRING);
            }
                
            matchToken(s.getToken());
            _Did = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U1]
            if (_Did.getCategory() == Symbol.NO_CATEGORY) {
                table.searchSymbol(_Did.getLexeme()).setCategory(Symbol.CATEGORY_VARIABLE);
            } else {
                System.err.println(lexical.line + ":identificador ja declarado [" + _Did.getLexeme() + "].");
                System.exit(0);
            }
            
            // semantic action [T5]
            table.searchSymbol(_Did.getLexeme()).setType(_D.getType());
            
            // semantic action [H1]
            _Dminus = false;
            
            // semantic action [H24]
            _Dinitialized = false;

            if (s.getToken() == SymbolTable.RECEIVE) {
                matchToken(SymbolTable.RECEIVE);

                if (s.getToken() == SymbolTable.MINUS) {
                    matchToken(SymbolTable.MINUS);
                    
                    // semantic action [T6]
                    if (_D.getType() != Symbol.TYPE_INTEGER) {
                        System.err.println(lexical.line + ":tipos incompativeis.");
                        System.exit(0);
                    } 
                    
                    // semantic action [H2]
                    _Dminus = true;
                }

                _Dconst = s;
                matchToken(SymbolTable.CONST);
                
                // semantic action [H25]
                _Dinitialized = true;
                
                // semantic action [T7]
                if ((_D.getType() != _Dconst.getType()) && (_D.getType() != Symbol.TYPE_INTEGER || _Dconst.getType() != Symbol.TYPE_BYTE)) {
                    System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                } 
            }

            while (s.getToken() == SymbolTable.COMMA) {
                matchToken(SymbolTable.COMMA);
                _Did = s;
                matchToken(SymbolTable.ID);
                
                // semantic action [U1]
                if (_Did.getCategory() == Symbol.NO_CATEGORY) {
                    table.searchSymbol(_Did.getLexeme()).setCategory(Symbol.CATEGORY_VARIABLE);
                } else {
                    System.err.println(lexical.line + ":identificador ja declarado [" + _Did.getLexeme() + "].");
                    System.exit(0);
                }
                
                // semantic action [T5]
                table.searchSymbol(_Did.getLexeme()).setType(_D.getType());

                // semantic action [H1]
                _Dminus = false;

                // semantic action [H24]
                _Dinitialized = false;

                if (s.getToken() == SymbolTable.RECEIVE) {
                    matchToken(SymbolTable.RECEIVE);

                    if (s.getToken() == SymbolTable.MINUS) {
                        matchToken(SymbolTable.MINUS);
                        
                        // semantic action [T6]
                        if (_D.getType() != Symbol.TYPE_INTEGER) {
                            System.err.println(lexical.line + ":tipos incompativeis.");
                            System.exit(0);
                        } 

                        // semantic action [H2]
                        _Dminus = true;
                    }

                    _Dconst = s;
                    matchToken(SymbolTable.CONST);
                    
                    // semantic action [H25]
                    _Dinitialized = true;

                    // semantic action [T7]
                    if ((_D.getType() != _Dconst.getType()) && (_D.getType() != Symbol.TYPE_INTEGER || _Dconst.getType() != Symbol.TYPE_BYTE)) {
                        System.err.println(lexical.line + ":tipos incompativeis.");
                        System.exit(0);
                    } 
                }
            }

            matchToken(SymbolTable.SEMICOLON);
        } // D -> FINAL ID RECEIVE [MINUS] CONST SEMICOLON
        else {
            matchToken(SymbolTable.FINAL);
            _Did = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U2]
            if (_Did.getCategory() == Symbol.NO_CATEGORY) {
                table.searchSymbol(_Did.getLexeme()).setCategory(Symbol.CATEGORY_CONSTANT);
            } else {
                System.err.println(lexical.line + ":identificador ja declarado [" + _Did.getLexeme() + "].");
                System.exit(0);
            }
            
            // semantic action [H1]
            _Dminus = false;
            
            matchToken(SymbolTable.RECEIVE);

            if (s.getToken() == SymbolTable.MINUS) {
                matchToken(SymbolTable.MINUS);

                // semantic action [H2]
                _Dminus = true;
            }

            _Dconst = s;
            matchToken(SymbolTable.CONST);
            
            // semantic action [T8]
            if (_Dminus && _Dconst.getType() != Symbol.TYPE_INTEGER) {
                System.err.println(lexical.line + ":tipos incompativeis.");
                System.exit(0);
            } else {
                table.searchSymbol(_Did.getLexeme()).setType(_Dconst.getType());
            }
            
            matchToken(SymbolTable.SEMICOLON);
        }
    }

    /**
     * Procedure that implements the commands.
     */
    private void C() {
        AuxiliarySymbol _E = new AuxiliarySymbol();
        Symbol _Cid;
        boolean _ClineBreak;
        
        // C -> ID RECEIVE E SEMICOLON
        if (s.getToken() == SymbolTable.ID) {
            _Cid = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U3]
            if (_Cid.getCategory() == Symbol.NO_CATEGORY) {
                System.err.println(lexical.line + ":identificador nao declarado [" + _Cid.getLexeme() + "].");
                System.exit(0);
            } 
            
            // semantic action [C1]
            if (_Cid.getCategory() != Symbol.CATEGORY_VARIABLE) {
                System.err.println(lexical.line + ":classe de identificador incompativel [" + _Cid.getLexeme() + "].");
                System.exit(0);
            }
            
            matchToken(SymbolTable.RECEIVE);
            E(_E);
            
            // semantic action [T9]
            if ((_Cid.getType() != _E.getType()) && (_Cid.getType() != Symbol.TYPE_INTEGER || _E.getType() != Symbol.TYPE_BYTE)) {
                System.err.println(lexical.line + ":tipos incompativeis.");
                System.exit(0);
            } 
            
            matchToken(SymbolTable.SEMICOLON);
        } // C -> WHILE OPPAR E CLPAR (BEGIN {C} ENDWHILE | C)
        else if (s.getToken() == SymbolTable.WHILE) {
            matchToken(SymbolTable.WHILE);
            matchToken(SymbolTable.OPPAR);
            E(_E);
            
            // semantic action [T10]
            if (_E.getType() != Symbol.TYPE_LOGICAL) {
                System.err.println(lexical.line + ":tipos incompativeis.");
                System.exit(0);
            }
            
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
            E(_E);
            
            // semantic action [T10]
            if (_E.getType() != Symbol.TYPE_LOGICAL) {
                System.err.println(lexical.line + ":tipos incompativeis.");
                System.exit(0);
            }
            
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
            _Cid = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U3]
            if (_Cid.getCategory() == Symbol.NO_CATEGORY) {
                System.err.println(lexical.line + ":identificador nao declarado [" + _Cid.getLexeme() + "].");
                System.exit(0);
            } 
            
            // semantic action [C1]
            if (_Cid.getCategory() != Symbol.CATEGORY_VARIABLE) {
                System.err.println(lexical.line + ":classe de identificador incompativel [" + _Cid.getLexeme() + "].");
                System.exit(0);
            }
            
            // semantic action [T11]
            if (_Cid.getType() == Symbol.TYPE_LOGICAL) {
                System.err.println(lexical.line + ":tipos incompativeis.");
                System.exit(0);
            }
            
            matchToken(SymbolTable.CLPAR);
            matchToken(SymbolTable.SEMICOLON);
        } // C -> (WRITE | WRITELN) OPPAR E {COMMA E} CLPAR SEMICOLON
        else {
            if (s.getToken() == SymbolTable.WRITE) {
                matchToken(SymbolTable.WRITE);
                
                // semantic action [H3]
                _ClineBreak = false;
            } else {
                matchToken(SymbolTable.WRITELN);
                
                // semantic action [H4]
                _ClineBreak = true;
            }

            matchToken(SymbolTable.OPPAR);
            E(_E);
            
            // semantic action [T12]
            if (_E.getType() == Symbol.TYPE_LOGICAL) {
                System.err.println(lexical.line + ":tipos incompativeis.");
                System.exit(0);
            }
            
            while (s.getToken() == SymbolTable.COMMA) {
                matchToken(SymbolTable.COMMA);
                E(_E);
                
                // semantic action [T12]
                if (_E.getType() == Symbol.TYPE_LOGICAL) {
                    System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                }
            }

            matchToken(SymbolTable.CLPAR);
            matchToken(SymbolTable.SEMICOLON);
        }
    }

    /**
     * Procedure that implements the expressions.
     */
    private void E(AuxiliarySymbol _E) {
        AuxiliarySymbol _X1 = new AuxiliarySymbol();
        byte _Eoperator;
        
        // E -> X [(LESSTHAN | MORETHAN | LESSEQUAL | MOREEQUAL | DIFFERENT | EQUALS) X]
        // semantic action [T13]
        X(_E);
        
        // semantic action [H5]
        _Eoperator = 0;

        if (s.getToken() == SymbolTable.LESSTHAN || s.getToken() == SymbolTable.MORETHAN || s.getToken() == SymbolTable.LESSEQUAL || s.getToken() == SymbolTable.MOREEQUAL || s.getToken() == SymbolTable.DIFFERENT || s.getToken() == SymbolTable.EQUALS) {
            // semantic action [H6], [H7], [H8], [H9], [H10], [H11]
            _Eoperator = s.getToken();
            
            matchToken(s.getToken());
            X(_X1);
            
            // semantic action [T14]
            if (_Eoperator != SymbolTable.EQUALS) {
                if (_E.getType() == Symbol.TYPE_LOGICAL || _E.getType() == Symbol.TYPE_STRING || _X1.getType() == Symbol.TYPE_LOGICAL || _X1.getType() == Symbol.TYPE_STRING) {
                    System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                }
            } else {
                if ((_E.getType() == Symbol.TYPE_LOGICAL || _X1.getType() == Symbol.TYPE_LOGICAL || (_E.getType() == Symbol.TYPE_STRING ^ _X1.getType() == Symbol.TYPE_STRING))) {
                    System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                }
            }
            _E.setType(Symbol.TYPE_LOGICAL);
        }
    }

    /**
     * Procedure that implements the simple expressions.
     */
    private void X(AuxiliarySymbol _X) {
        AuxiliarySymbol _T1 = new AuxiliarySymbol();
        boolean _Xoption, _Xminus, _Xplus, _Xlogical;
        
        // semantic action [H12]
        _Xoption = false;
        
        // X -> [PLUS | MINUS] T {(PLUS | MINUS | OR) T}
        if (s.getToken() == SymbolTable.PLUS || s.getToken() == SymbolTable.MINUS) {
            // semantic action [H13]
            _Xoption = true;
            
            // semantic action [H14]
            if (s.getToken() == SymbolTable.PLUS) {
                _Xminus = false;
            }
            // semantic action [H15]
            else {
                _Xminus = true;
            }
            matchToken(s.getToken());
        }
        
        // semantic action [T15]
        T(_X);
        
        // semantic action [T16]
        if (_Xoption && (_X.getType() == Symbol.TYPE_STRING || _X.getType() == Symbol.TYPE_LOGICAL)) {
            System.err.println(lexical.line + ":tipos incompativeis.");
            System.exit(0);
        }

        while (s.getToken() == SymbolTable.PLUS || s.getToken() == SymbolTable.MINUS || s.getToken() == SymbolTable.OR) {
            // semantic action [H17]
            if (s.getToken() == SymbolTable.OR) {
                _Xlogical = true;
                _Xplus = false;
            }
            // semantic actino [H16] 
            else {
                _Xlogical = false;
                
                // semantic action [H18]
                if (s.getToken() == SymbolTable.PLUS) {
                    _Xplus = true;
                }
                // semantic action [H19]
                else {
                    _Xplus = false;
                }
            }
            matchToken(s.getToken());
            T(_T1);
            
            // semantic action [T17]
            if (_Xlogical) {
                if (_X.getType() != Symbol.TYPE_LOGICAL || _T1.getType()!= Symbol.TYPE_LOGICAL) {
                    System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                }
            } else {
                if (!_Xplus) {
                    if (_X.getType() == Symbol.TYPE_LOGICAL || _X.getType() == Symbol.TYPE_STRING || _T1.getType() == Symbol.TYPE_LOGICAL || _T1.getType() == Symbol.TYPE_STRING) {
                        System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                    } else if (_X.getType() != _T1.getType()){
                        _X.setType(Symbol.TYPE_INTEGER);
                    }
                } else {
                    if ((_X.getType() == Symbol.TYPE_LOGICAL || _X.getType() == Symbol.TYPE_STRING || _T1.getType() == Symbol.TYPE_LOGICAL || _T1.getType() == Symbol.TYPE_STRING) && (_X.getType() != Symbol.TYPE_STRING && _T1.getType() != Symbol.TYPE_STRING)) {
                        System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                    } else if (_X.getType() != _T1.getType()){
                        _X.setType(Symbol.TYPE_INTEGER);
                    }
                }
            }
        }
    }

    /**
     * Procedure that implements the terms.
     */
    private void T(AuxiliarySymbol _T) {
        AuxiliarySymbol _F1 = new AuxiliarySymbol();
        boolean _Tlogical, _Ttimes;
        
        // T -> F {(TIMES | DIVIDE | AND) F}        
        // semantic action [T18]
        F(_T);

        while (s.getToken() == SymbolTable.TIMES || s.getToken() == SymbolTable.DIVIDE || s.getToken() == SymbolTable.AND) {
            // semantic action [H21]
            if (s.getToken() == SymbolTable.AND) {
                _Tlogical = true;
                _Ttimes = false;
            }
            // semantic action [H20]
            else {
                _Tlogical = false;
                
                // semantic action [H22]
                if (s.getToken() == SymbolTable.TIMES) {
                    _Ttimes = true;
                }
                // semantic action [H23]
                else {
                    _Ttimes = false;
                }
            }
            matchToken(s.getToken());
            F(_F1);
            
            // semantic action [T19]
            if (_Tlogical) {
                if (_T.getType() != Symbol.TYPE_LOGICAL || _F1.getType() != Symbol.TYPE_LOGICAL) {
                    System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                }
            } else {
                if (_T.getType() == Symbol.TYPE_LOGICAL ||_T.getType() == Symbol.TYPE_STRING || _F1.getType() == Symbol.TYPE_LOGICAL || _F1.getType() == Symbol.TYPE_STRING) {
                    System.err.println(lexical.line + ":tipos incompativeis.");
                    System.exit(0);
                } else if (_T.getType() != _F1.getType()) {
                    _T.setType(Symbol.TYPE_INTEGER);
                }
            }
        }
    }

    /**
     * Procedure that implements the factors.
     */
    private void F(AuxiliarySymbol _F) {
        AuxiliarySymbol _F1 = new AuxiliarySymbol();
        Symbol _Fid, _Fconst;
        
        // F -> OPPAR E CLPAR
        if (s.getToken() == SymbolTable.OPPAR) {
            matchToken(SymbolTable.OPPAR);
            
            // semantic action [T20]
            E(_F);
            
            matchToken(SymbolTable.CLPAR);
        } // F -> ID
        else if (s.getToken() == SymbolTable.ID) {
            _Fid = s;
            matchToken(SymbolTable.ID);
            
            // semantic action [U3]
            if (_Fid.getCategory() == Symbol.NO_CATEGORY) {
                System.err.println(lexical.line + ":identificador nao declarado [" + _Fid.getLexeme() + "].");
                System.exit(0);
            } 
            
            // semantic action [T21]
            _F.setType(table.searchSymbol(_Fid.getLexeme()).getType());
        } // F -> CONST
        else if (s.getToken() == SymbolTable.CONST) {
            _Fconst = s;
            matchToken(SymbolTable.CONST);
            
            // semantic action [T22]
            _F.setType(_Fconst.getType());
        } // F -> NOT F
        else {
            matchToken(SymbolTable.NOT);
            F(_F1);
            
            // semantic action [T23]
            if (_F1.getType() != Symbol.TYPE_LOGICAL) {
                System.err.println(lexical.line + ":tipos incompativeis.");
                System.exit(0);
            } else {
                _F.setType(Symbol.TYPE_LOGICAL);
            }
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
