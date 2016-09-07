

import java.io.BufferedReader;

public class LexicalAnalyzer {

    SymbolTable symbols = new SymbolTable();
    String lexeme = "";
    boolean ler = true;
    String file = "";
    private char c;
    public static int line = 1;
    public boolean comment = false;
    public boolean EOF = false;
    BufferedReader reader;

    public String getNextLexeme(BufferedReader file) throws Exception {
        int currentState = 0;
        final int finalState = 15;

        while (currentState != finalState) {
            switch (currentState) {
                case 0:
                    file.mark(2);
                    c = (char) file.read();
                    lexeme = "";

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
                    } else if (isDigit(c) && c != '0') {
                        lexeme += c;
                        currentState = 2;
                    } else if (c == '0') {
                        lexeme += c;
                        currentState = 3;
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
                        System.err.println(line + ":caractere invalido.");
                        System.exit(0);
                    }
                    break;

                case 1:
                    file.mark(2);
                    c = (char) file.read();

                    if (isLetter(c) || isDigit(c) || c == '_') {
                        lexeme += c;
                        currentState = 1;
                    } else {
                        // ID
                        file.reset();
                        currentState = finalState;
                    }
                    break;

                case 2:
                    file.mark(2);
                    c = (char) file.read();

                    if (isDigit(c)) {
                        lexeme += c;
                        currentState = 2;
                    } else {
                        // CONST
                        file.reset();
                        currentState = finalState;
                    }
                    break;

                case 3:
                    file.mark(2);
                    c = (char) file.read();

                    if (isDigit(c)) {
                        lexeme += c;
                        currentState = 2;
                    } else if (c == 'x') {
                        lexeme += c;
                        currentState = 11;
                    } else {
                        // CONST
                        file.reset();
                        currentState = finalState;
                    }
                    break;

                case 4:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '*') {
                        lexeme += c;
                        currentState = 13;
                    } else {
                        // DIVIDE
                        file.reset();
                        currentState = finalState;
                    }
                    break;

                case 5:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '&') {
                        // AND
                        lexeme += c;
                        currentState = finalState;
                    } else {
                        System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                        System.exit(0);
                    }
                    break;

                case 6:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '|') {
                        // OR
                        lexeme += c;
                        currentState = finalState;
                    } else {
                        System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                        System.exit(0);
                    }
                    break;

                case 7:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '=') {
                        // DIFFERENT
                        lexeme += c;
                        currentState = finalState;
                    } else {
                        // NOT
                        file.reset();
                        currentState = finalState;
                    }
                    break;

                case 8:
                    file.mark(2);
                    c = (char) file.read();

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
                        file.reset();
                        currentState = finalState;
                    }
                    break;

                case 9:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '=') {
                        // MOREEQUAL
                        lexeme += c;
                        currentState = finalState;
                    } else {
                        // MORETHAN
                        file.reset();
                        currentState = finalState;
                    }
                    break;

                case 10:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '\"') {
                        // STRING
                        lexeme += c;
                        currentState = finalState;
                    } else if (c == 10) {
                        System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                        System.exit(0);
                    } else {
                        lexeme += c;
                        currentState = 10;
                    }
                    break;

                case 11:
                    file.mark(2);
                    c = (char) file.read();

                    if (isHexadecimal(c)) {
                        lexeme += c;
                        currentState = 12;
                    } else {
                        System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                        System.exit(0);
                    }
                    break;

                case 12:
                    file.mark(2);
                    c = (char) file.read();

                    if (isHexadecimal(c)) {
                        // CONST
                        lexeme += c;
                        currentState = finalState;
                    } else {
                        System.err.println(line + ":lexema nao indentificao [" + lexeme + "]");
                        System.exit(0);
                    }
                    break;

                case 13:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '*') {
                        lexeme += c;
                        currentState = 14;
                    } else {
                        lexeme += c;
                        currentState = 13;
                    }
                    break;

                case 14:
                    file.mark(2);
                    c = (char) file.read();

                    if (c == '/') {
                        // COMMENT
                        lexeme += c;
                        currentState = finalState;
                    } else if (c == '*') {
                        lexeme += c;
                        currentState = 14;
                    } else {
                        lexeme += c;
                        currentState = 13;
                    }
                    break;

            }

        }

        return lexeme;
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private boolean isLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    private boolean isHexadecimal(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

}
