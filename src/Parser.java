
import java.io.BufferedReader;

public class Parser {

    BufferedReader file;
    LexicalAnalyzer lexical;
    SymbolTable table;
    Symbol s;

    public Parser(BufferedReader source) {
        try {
            file = source;
            lexical = new LexicalAnalyzer();
            table = new SymbolTable();
            s = lexical.getNextLexeme(file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void matchToken(byte token) {
        try {
            if (s.getToken() == token) {
                System.out.println(s.getLexeme());
                s = lexical.getNextLexeme(file);
            } else if (lexical.EOF) {
                System.err.println(LexicalAnalyzer.line + ":fim de arquivo nao esperado.");
                System.exit(0);
            } else {
                System.err.println(LexicalAnalyzer.line + ":token nao esperado [" + s.getLexeme() + "]");
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void S() throws Exception {
        while (s.getToken() == table.INT || s.getToken() == table.BOOLEAN || s.getToken() == table.BYTE || s.getToken() == table.STRING || s.getToken() == table.FINAL) {
            D();
        }
        do {
            C();
        } while (s.getToken() == table.ID || s.getToken() == table.WHILE || s.getToken() == table.SEMICOLON || s.getToken() == table.READLN || s.getToken() == table.WRITE || s.getToken() == table.WRITELN);

        if (!lexical.EOF) {
            System.err.println(LexicalAnalyzer.line + ":token nao esperado [" + s.getLexeme() + "]");
            System.exit(0);
        }
    }

    private void D() throws Exception {
        if (s.getToken() == table.INT || s.getToken() == table.BOOLEAN || s.getToken() == table.BYTE || s.getToken() == table.STRING) {
            matchToken(s.getToken());
            matchToken(table.ID);

            if (s.getToken() == table.RECEIVE) {
                matchToken(table.RECEIVE);

                if (s.getToken() == table.MINUS) {
                    matchToken(table.MINUS);
                }

                matchToken(table.CONST);
            }

            while (s.getToken() == table.COMMA) {
                matchToken(table.COMMA);
                matchToken(table.ID);

                if (s.getToken() == table.RECEIVE) {
                    matchToken(table.RECEIVE);

                    if (s.getToken() == table.MINUS) {
                        matchToken(table.MINUS);
                    }

                    matchToken(table.CONST);
                }
            }

            matchToken(table.SEMICOLON);
        } else {
            matchToken(table.FINAL);
            matchToken(table.ID);
            matchToken(table.RECEIVE);

            if (s.getToken() == table.MINUS) {
                matchToken(table.MINUS);
            }

            matchToken(table.CONST);
            matchToken(table.SEMICOLON);
        }
    }

    private void C() throws Exception {
        if (s.getToken() == table.ID) {
            matchToken(table.ID);
            matchToken(table.RECEIVE);
            E();
            matchToken(table.SEMICOLON);
        } else if (s.getToken() == table.WHILE) {
            matchToken(table.WHILE);
            matchToken(table.OPPAR);
            E();
            matchToken(table.CLPAR);

            if (s.getToken() == table.BEGIN) {
                matchToken(table.BEGIN);

                while (s.getToken() == table.ID || s.getToken() == table.WHILE || s.getToken() == table.SEMICOLON || s.getToken() == table.READLN || s.getToken() == table.WRITE || s.getToken() == table.WRITELN) {
                    C();
                }

                matchToken(table.ENDWHILE);
            } else {
                C();
            }
        } else if (s.getToken() == table.IF) {
            matchToken(table.IF);
            matchToken(table.OPPAR);
            E();
            matchToken(table.CLPAR);

            if (s.getToken() == table.BEGIN) {
                matchToken(table.BEGIN);

                while (s.getToken() == table.ID || s.getToken() == table.WHILE || s.getToken() == table.SEMICOLON || s.getToken() == table.READLN || s.getToken() == table.WRITE || s.getToken() == table.WRITELN) {
                    C();
                }

                matchToken(table.ENDIF);

                if (s.getToken() == table.ELSE) {
                    matchToken(table.ELSE);
                    matchToken(table.BEGIN);

                    while (s.getToken() == table.ID || s.getToken() == table.WHILE || s.getToken() == table.SEMICOLON || s.getToken() == table.READLN || s.getToken() == table.WRITE || s.getToken() == table.WRITELN) {
                        C();
                    }

                    matchToken(table.ENDELSE);
                }

                matchToken(table.ENDWHILE);
            } else {
                C();

                if (s.getToken() == table.ELSE) {
                    matchToken(table.ELSE);
                    C();
                }
            }
        } else if (s.getToken() == table.SEMICOLON) {
            matchToken(table.SEMICOLON);
        } else if (s.getToken() == table.READLN) {
            matchToken(table.READLN);
            matchToken(table.OPPAR);
            matchToken(table.ID);
            matchToken(table.CLPAR);
            matchToken(table.SEMICOLON);
        } else {
            if (s.getToken() == table.WRITE) {
                matchToken(table.WRITE);
            } else {
                matchToken(table.WRITELN);
            }

            matchToken(table.OPPAR);
            E();
            while (s.getAddress() == table.COMMA) {
                matchToken(table.COMMA);
                E();

            }
            matchToken(table.CLPAR);
            matchToken(table.SEMICOLON);
        }
    }

    private void E() throws Exception {
        X();

        if (s.getToken() == table.LESSTHAN || s.getToken() == table.MORETHAN || s.getToken() == table.LESSEQUAL || s.getToken() == table.MORETHAN || s.getToken() == table.DIFFERENT || s.getToken() == table.EQUALS) {
            matchToken(s.getToken());
            X();
        }
    }

    private void X() throws Exception {
        if (s.getToken() == table.PLUS || s.getToken() == table.MINUS) {
            matchToken(s.getToken());
        }

        T();

        while (s.getToken() == table.PLUS || s.getToken() == table.MINUS || s.getToken() == table.OR) {
            matchToken(s.getToken());
            T();
        }
    }

    private void T() throws Exception {
        F();

        while (s.getToken() == table.MINUS || s.getToken() == table.DIVIDE || s.getToken() == table.AND) {
            matchToken(s.getToken());
            F();
        }
    }

    private void F() throws Exception {
        if (s.getToken() == table.OPPAR) {
            matchToken(table.OPPAR);
            E();
            matchToken(table.CLPAR);
        } else if (s.getToken() == table.ID) {
            matchToken(table.ID);
        } else if (s.getToken() == table.CONST) {
            matchToken(table.CONST);
        } else {
            matchToken(table.NOT);
            F();
        }
    }
}
