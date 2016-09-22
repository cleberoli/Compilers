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
 * Reads the file and processes each character in order to 
 * identify the tokens in the source file.
 */
public class LexicalAnalyzer {

    SymbolTable symbols;
    private String lexeme;
    private char c;
    private Symbol s;

    /**
     * The current line being processed. 
     */
    public int line;
    
    /**
     * Return variable, when it's true it means that we have read a character 
     * that doesn't belong to the previous lexeme so we have to return it.
     */
    public boolean ret;
    
    /**
     * End of file.
     */
    public boolean EOF;

    /**
     * Default constructor, it initializes the variables that will be used 
     * during its execution, setting ret = false and EOF = false.
     * @param table SymbolTable that will contain all references to be tokens.
     */
    public LexicalAnalyzer(SymbolTable table) {
        line = 1;
        symbols = table;
        ret = false;
        EOF = false;
    }

    /**
     * Processes each character and verifies if they correspond to a known token 
     * of the language L. This method is implemented based on the automaton 
     * presented with the documentation. 
     * @param file BufferedReader containing the information of the source file.
     * @return Symbol s if the characters represents a token of the language.
     * @throws Exception IOExecption due to file reading
     */
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
                        } else if (isDigit(c)) { // is different from 0
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
                        } else if (c == -1 || c == 65535) {
                            currentState = finalState;
                            lexeme += c;
                            EOF = true;
                            file.close();
                        } else {
                            lexeme += c;
                            System.err.println(line + ":lexema nao indentificado [" + lexeme + "].");
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
                            System.err.println(line + ":lexema nao indentificado [" + lexeme + "].");
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
                            System.err.println(line + ":lexema nao indentificado [" + lexeme + "].");
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
                            System.err.println(line + ":lexema nao indentificado [" + lexeme + "].");
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
                            System.err.println(line + ":lexema nao indentificado [" + lexeme + "].");
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
                            System.err.println(line + ":lexema nao indentificado [" + lexeme + "].");
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
                s = symbols.insertConst(lexeme);
            } else if (symbols.searchSymbol(lexeme) != null) {
                // token has already been inserted in the table
                // covers all the reserved words that are inserted in the table
                // at the begin of the execution
                s = symbols.searchSymbol(lexeme);
            } else if (isLetter(lexeme.charAt(0)) || lexeme.charAt(0) == '_') {
                // ID identified, tests if it has at most 255 characters
                if (lexeme.length() >= 255) {
                    System.err.println(line + ":lexema nao indentificao [" + lexeme + "].");
                    System.exit(0);
                } else {
                    s = symbols.insertId(lexeme);
                }
            } else if (lexeme.charAt(0) == '\"') {
                // STRING identified, tests if it has at most 256 characters
                if (lexeme.length() >= 256) {
                    System.err.println(line + ":lexema nao indentificao [" + lexeme + "].");
                    System.exit(0);
                } else {
                    lexeme = lexeme.substring(1, lexeme.length() - 2) + "$";
                }
                s = symbols.insertConst(lexeme);
            } else {
                // it can only be a CONST
                s = symbols.insertConst(lexeme);
            }
        } else {
            // returns a fake symbol (EOF) in order to make future comparisons work
            s = new Symbol("EOF", Byte.MAX_VALUE, -1);
        }

        return s;
    }

    /**
     * Tests if the character is a decimal digit.
     * @param c Character to be tested
     * @return true if it is a decimal digit and false otherwise.
     */
    private static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    /**
     * Tests if the character is a letter.
     * @param c Character to be tested
     * @return true if it is a letter and false otherwise.
     */
    private static boolean isLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    /**
     * Tests if the character is a hexadecimal digit.
     * @param c Character to be tested
     * @return true if it is a hexadecimal digit and false otherwise.
     */
    private static boolean isHexadecimal(char c) {
        return ('0' <= c && c <= '9') || ('A' <= c && c <= 'F');
    }

    /**
     * Tests if the character is an allowed character in the file.
     * @param c Character to be tested
     * @return true if it is an allowed character and false otherwise.
     */
    private static boolean isCharacter(char c) {
        // 8 - back-space (\b)
        // 9 - horizontal tabulation (\t)
        // 10 - line feed (\n)
        // 11 - vertical tabulation (\v)
        // 13 - carriage return (\r)
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
        // 65535 - eof
        return isLetter(c) || isDigit(c) || (32 <= c && c <= 34) || (38 <= c && c <= 47) || (58 <= c && c <= 63) || c == 91 || c == 93 || c == 95 || (123 <= c && c <= 125) || (8 <= c && c <= 11) || c == 13 || c == 32 || c == 65535 || c == -1;
    }

}