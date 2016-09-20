
import java.io.BufferedReader;

public class LexicalAnalyzer {

    SymbolTable symbols = new SymbolTable();
    private String lexeme;
    private char c;
    private Symbol s;
    public static int line = 1;
    public boolean ret;
    public boolean EOF;

    public LexicalAnalyzer() {
        ret = false;
        EOF = false;
        lexeme = "";
    }

    public Symbol getNextLexeme(BufferedReader file) throws Exception {
        int currentState = 0;
        final int finalState = 15;

        while (currentState != finalState) {
            switch (currentState) {
                case 0:
                    if (!ret) {
                        c = (char) file.read();
                    }

                    ret = false;
                    lexeme = "";

                    if (isCharacter(c)) {
                        if (c == 10 || c == 11) {
                            // line feed(\n), vertical tabulation(\v)
                            currentState = 0;
                            line++;
                        } else if (c == 8 || c == 9 || c == 13 || c == 32) {
                            // back-space(\b), horizontal tabulation(\t), carriage return(\r), space
                            currentState = 0;
                        } else if (c == '=' || c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == ',' || c == ';') {
                            // EQUALS, OPPAR, CLPAR, PLUS, MINUS, TIMES, COMMA, SEMICOLON
                            lexeme += c;
                            currentState = finalState;
                        } else if (isLetter(c) || c == '_') {
                            lexeme += c;
                            currentState = 1;
                        } else if (c == '0') {
                            lexeme += c;
                            currentState = 3;
                        } else if (isDigit(c)) {
                            lexeme += c;
                            currentState = 2;
                        } else if (c == '/') {
                            lexeme += c;
                            currentState = 4;
                        } else if (c == '&') {
                            lexeme += c;
                            currentState = 5;
                        } else if (c == '|') {
                            lexeme += c;
                            currentState = 6;
                        } else if (c == '!') {
                            lexeme += c;
                            currentState = 7;
                        } else if (c == '<') {
                            lexeme += c;
                            currentState = 8;
                        } else if (c == '>') {
                            lexeme += c;
                            currentState = 9;
                        } else if (c == '\"') {
                            lexeme += c;
                            currentState = 10;
                        } else if (c == 65535) {
                            currentState = finalState;
                            lexeme += c;
                            EOF = true;
                            file.close();
                        } else {
                            lexeme += c;
                            System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                            System.exit(0);
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 1:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (isLetter(c) || isDigit(c) || c == '_') {
                            lexeme += c;
                            currentState = 1;
                        } else {
                            // ID
                            ret = true;
                            currentState = finalState;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 2:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (isDigit(c)) {
                            lexeme += c;
                            currentState = 2;
                        } else {
                            // CONST
                            ret = true;
                            currentState = finalState;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 3:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (isDigit(c)) {
                            lexeme += c;
                            currentState = 2;
                        } else if (c == 'x') {
                            lexeme += c;
                            currentState = 11;
                        } else {
                            // CONST
                            ret = true;
                            currentState = finalState;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 4:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '*') {
                            lexeme += c;
                            currentState = 13;
                        } else {
                            // DIVIDE
                            ret = true;
                            currentState = finalState;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 5:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '&') {
                            // AND
                            lexeme += c;
                            currentState = finalState;
                        } else if (c == -1) {
                            System.err.println(line + ":fim de arquivo nao esperado.");
                            System.exit(0);
                        } else {
                            lexeme += c;
                            System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                            System.exit(0);
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 6:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '|') {
                            // OR
                            lexeme += c;
                            currentState = finalState;
                        } else if (c == -1) {
                            System.err.println(line + ":fim de arquivo nao esperado.");
                            System.exit(0);
                        } else {
                            lexeme += c;
                            System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                            System.exit(0);
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 7:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '=') {
                            // DIFFERENT
                            lexeme += c;
                            currentState = finalState;
                        } else {
                            // NOT
                            ret = true;
                            currentState = finalState;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 8:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '-') {
                            // RECEIVE
                            lexeme += c;
                            currentState = finalState;
                        } else if (c == '=') {
                            // LESSEQUAL
                            lexeme += c;
                            currentState = finalState;
                        } else {
                            // LESSTHAN
                            ret = true;
                            currentState = finalState;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 9:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '=') {
                            // MOREEQUAL
                            lexeme += c;
                            currentState = finalState;
                        } else {
                            // MORETHAN
                            ret = true;
                            currentState = finalState;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 10:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '\"') {
                            // STRING
                            lexeme += c;
                            currentState = finalState;
                        } else if (c == 10) {
                            System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                            System.exit(0);
                        } else if (c == -1) {
                            System.err.println(line + ":fim de arquivo nao esperado.");
                            System.exit(0);
                        } else {
                            lexeme += c;
                            currentState = 10;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 11:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (isHexadecimal(c)) {
                            lexeme += c;
                            currentState = 12;
                        } else if (c == -1) {
                            System.err.println(line + ":fim de arquivo nao esperado.");
                            System.exit(0);
                        } else {
                            lexeme += c;
                            System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                            System.exit(0);
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 12:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (isHexadecimal(c)) {
                            // CONST
                            lexeme += c;
                            currentState = finalState;
                        } else if (c == -1) {
                            System.err.println(line + ":fim de arquivo nao esperado.");
                            System.exit(0);
                        } else {
                            lexeme += c;
                            System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                            System.exit(0);
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 13:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '*') {
                            lexeme += c;
                            currentState = 14;
                        } else if (c == -1) {
                            System.err.println(line + ":fim de arquivo nao esperado.");
                            System.exit(0);
                        } else {
                            lexeme += c;
                            currentState = 13;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 14:
                    c = (char) file.read();

                    if (isCharacter(c)) {
                        if (c == '/') {
                            // COMMENT
                            lexeme += c;
                            currentState = 0;
                        } else if (c == '*') {
                            lexeme += c;
                            currentState = 14;
                        } else if (c == -1) {
                            System.err.println(line + ":fim de arquivo nao esperado.");
                            System.exit(0);
                        } else {
                            lexeme += c;
                            currentState = 13;
                        }
                    } else {
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;
            }
        }

        if (!EOF) {
            if (lexeme.equals("TRUE") || lexeme.equals("FALSE")) {
                s = symbols.insertConst(lexeme, "");
            } else if (symbols.searchSymbol(lexeme) != null) {
                s = symbols.searchSymbol(lexeme);
            } else if (isLetter(lexeme.charAt(0)) || lexeme.charAt(0) == '_') {
                s = symbols.insertId(lexeme);
            } else if (lexeme.charAt(0) == '\"') {
                s = symbols.insertConst(lexeme, "");
            } else {
                s = symbols.insertConst(lexeme, "");
            }
        } else {
            s = new Symbol("EOF", Byte.MAX_VALUE, -1);
        }

        return s;
    }

    private static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    private static boolean isHexadecimal(char c) {
        return ('0' <= c && c <= '9') || ('A' <= c && c <= 'F');
    }

    private static boolean isCharacter(char c) {
        // 32 - spcace
        // 33 - !
        // 34 - "
        // 38 - &
        // 39 - '
        // 40 - (
        // 41 - )
        // 42 - *
        // 43 - +
        // 44 - ,
        // 45 - -
        // 46 - .
        // 47 - /
        // 58 - :
        // 59 - ;
        // 60 - <
        // 61 - =
        // 62 - >
        // 63 - ?
        // 91 - [
        // 93 - ]
        // 95 - _
        // 123 - {
        // 124 - |
        // 125 - }
        return isLetter(c) || isDigit(c) || (32 <= c && c <= 34) || (38 <= c && c <= 47) || (58 <= c && c <= 63) || c == 91 || c == 93 || c == 95 || (123 <= c && c <= 125) || (8 <= c && c <= 11) || c == 13 || c == 32 || c == -1 || c == 65535;
    }

}
