/*
* Pontifical Catholic University of Minas Gerais
* Institue of Exact Sciences and Technology
* Compilers
*
* Authors: Cleber Oliveira, Karen Martins, and Sarah Almeida
* IDs: 486564, 476140, 476181
 */

import java.util.HashMap;

/**
 * Table containing the reserved words and the IDs contained in the source
 * files.
 */
public class SymbolTable {

    public HashMap<String, Symbol> table;

    public static final byte BEGIN = 0;
    public static final byte FINAL = 1;
    public static final byte WHILE = 2;
    public static final byte ENDWHILE = 3;
    public static final byte IF = 4;
    public static final byte ENDIF = 5;
    public static final byte ELSE = 6;
    public static final byte ENDELSE = 7;
    public static final byte READLN = 8;
    public static final byte WRITE = 9;
    public static final byte WRITELN = 10;
    public static final byte TRUE = 11;
    public static final byte FALSE = 12;
    public static final byte BYTE = 13;
    public static final byte BOOLEAN = 14;
    public static final byte INT = 15;
    public static final byte STRING = 16;
    public static final byte AND = 17;
    public static final byte OR = 18;
    public static final byte NOT = 19;
    public static final byte RECEIVE = 20;
    public static final byte EQUALS = 21;
    public static final byte OPPAR = 22;
    public static final byte CLPAR = 23;
    public static final byte LESSTHAN = 24;
    public static final byte MORETHAN = 25;
    public static final byte DIFFERENT = 26;
    public static final byte LESSEQUAL = 27;
    public static final byte MOREEQUAL = 28;
    public static final byte PLUS = 29;
    public static final byte MINUS = 30;
    public static final byte TIMES = 31;
    public static final byte DIVIDE = 32;
    public static final byte COMMA = 33;
    public static final byte SEMICOLON = 34;

    public static final byte ID = 35;
    public static final byte CONST = 36;

    public static final byte EOF = Byte.MAX_VALUE;

    /**
     * Default constructor, it initializes the HashTable and adds the reserved
     * words to the table
     */
    public SymbolTable() {
        table = new HashMap<>();
        table.put("begin", new Symbol("begin", BEGIN));
        table.put("final", new Symbol("final", FINAL));
        table.put("while", new Symbol("while", WHILE));
        table.put("endwhile", new Symbol("endwhile", ENDWHILE));
        table.put("if", new Symbol("if", IF));
        table.put("endif", new Symbol("endif", ENDIF));
        table.put("else", new Symbol("else", ELSE));
        table.put("endelse", new Symbol("endelse", ENDELSE));
        table.put("readln", new Symbol("readln", READLN));
        table.put("write", new Symbol("write", WRITE));
        table.put("writeln", new Symbol("writeln", WRITELN));
        table.put("TRUE", new Symbol("TRUE", TRUE));
        table.put("FALSE", new Symbol("FALSE", FALSE));
        table.put("byte", new Symbol("byte", BYTE));
        table.put("boolean", new Symbol("boolean", BOOLEAN));
        table.put("int", new Symbol("int", INT));
        table.put("string", new Symbol("string", STRING));
        table.put("&&", new Symbol("&&", AND));
        table.put("||", new Symbol("||", OR));
        table.put("!", new Symbol("!", NOT));
        table.put("<-", new Symbol("<-", RECEIVE));
        table.put("=", new Symbol("=", EQUALS));
        table.put("(", new Symbol("(", OPPAR));
        table.put(")", new Symbol(")", CLPAR));
        table.put("<", new Symbol("<", LESSTHAN));
        table.put(">", new Symbol(">", MORETHAN));
        table.put("!=", new Symbol("!=", DIFFERENT));
        table.put("<=", new Symbol("<=", LESSEQUAL));
        table.put(">=", new Symbol(">=", MOREEQUAL));
        table.put("+", new Symbol("+", PLUS));
        table.put("-", new Symbol("-", MINUS));
        table.put("*", new Symbol("*", TIMES));
        table.put("/", new Symbol("/", DIVIDE));
        table.put(",", new Symbol(",", COMMA));
        table.put(";", new Symbol(";", SEMICOLON));
    }

    /**
     * Searches the table for the desired lexeme
     *
     * @param lexeme desired lexeme
     * @return address in the table
     */
    public int searchAddress(String lexeme) {
        return table.get(lexeme).getAddress();
    }

    /**
     * Searches the table for the desired lexeme
     *
     * @param lexeme desired lexeme
     * @return symbol in the table
     */
    public Symbol searchSymbol(String lexeme) {
        return table.get(lexeme);
    }

    /**
     * Creates a new symbol with and inserts it into the table.
     *
     * @param lexeme new ID's lexeme
     * @return reference to the new symbol in the table
     */
    public Symbol insertId(String lexeme) {
        table.put(lexeme, new Symbol(lexeme, ID));
        return table.get(lexeme);
    }

}