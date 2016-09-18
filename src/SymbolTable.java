

import java.util.HashMap;

public class SymbolTable {

    public HashMap<String, Symbol> table;
    public static int add = -1;

    public final byte BEGIN = 0;
    public final byte FINAL = 1;
    public final byte WHILE = 2;
    public final byte ENDWHILE = 3;
    public final byte IF = 4;
    public final byte ENDIF = 5;
    public final byte ELSE = 6;
    public final byte ENDELSE = 7;
    public final byte READLN = 8;
    public final byte WRITE = 9;
    public final byte WRITELN = 10;
    public final byte TRUE = 11;
    public final byte FALSE = 12;
    public final byte BYTE = 13;
    public final byte BOOLEAN = 14;
    public final byte INT = 15;
    public final byte STRING = 16;
    public final byte AND = 17;
    public final byte OR = 18;
    public final byte NOT = 19;
    public final byte RECEIVE = 20;
    public final byte EQUALS = 21;
    public final byte OPPAR = 22;
    public final byte CLPAR = 23;
    public final byte LESSTHAN = 24;
    public final byte MORETHAN = 25;
    public final byte DIFFERENT = 26;
    public final byte LESSEQUAL = 27;
    public final byte MOREEQUAL = 28;
    public final byte PLUS = 29;
    public final byte MINUS = 30;
    public final byte TIMES = 31;
    public final byte DIVIDE = 32;
    public final byte COMMA = 33;
    public final byte SEMICOLON = 34;

    public final byte ID = 35;
    public final byte CONST = 36;

    public SymbolTable() {
        table = new HashMap<>();
        table.put("begin", new Symbol("begin", BEGIN, ++add));
        table.put("final", new Symbol("final", FINAL, ++add));
        table.put("while", new Symbol("while", WHILE, ++add));
        table.put("endwhile", new Symbol("endwhile", ENDWHILE, ++add));
        table.put("if", new Symbol("if", IF, ++add));
        table.put("endif", new Symbol("endif", ENDIF, ++add));
        table.put("else", new Symbol("else", ELSE, ++add));
        table.put("endelse", new Symbol("endelse", ENDELSE, ++add));
        table.put("readln", new Symbol("readln", READLN, ++add));
        table.put("write", new Symbol("write", WRITE, ++add));
        table.put("writeln", new Symbol("writeln", WRITELN, ++add));
        table.put("TRUE", new Symbol("TRUE", TRUE, ++add));
        table.put("FALSE", new Symbol("FALSE", FALSE, ++add));
        table.put("byte", new Symbol("byte", BYTE, ++add));
        table.put("boolean", new Symbol("boolean", BOOLEAN, ++add));
        table.put("int", new Symbol("int", INT, ++add));
        table.put("string", new Symbol("string", STRING, ++add));
        table.put("&&", new Symbol("&&", AND, ++add));
        table.put("||", new Symbol("||", OR, ++add));
        table.put("!", new Symbol("!", NOT, ++add));
        table.put("<-", new Symbol("<-", RECEIVE, ++add));
        table.put("=", new Symbol("=", EQUALS, ++add));
        table.put("(", new Symbol("(", OPPAR, ++add));
        table.put(")", new Symbol(")", CLPAR, ++add));
        table.put("<", new Symbol("<", LESSTHAN, ++add));
        table.put(">", new Symbol(">", MORETHAN, ++add));
        table.put("!=", new Symbol("!=", DIFFERENT, ++add));
        table.put("<=", new Symbol("<=", LESSEQUAL, ++add));
        table.put(">=", new Symbol(">=", MOREEQUAL, ++add));
        table.put("+", new Symbol("+", PLUS, ++add));
        table.put("-", new Symbol("-", MINUS, ++add));
        table.put("*", new Symbol("*", TIMES, ++add));
        table.put("/", new Symbol("/", DIVIDE, ++add));
        table.put(",", new Symbol(",", COMMA, ++add));
        table.put(";", new Symbol(";", SEMICOLON, ++add));
    }

    public int searchAddress(String lexeme) {
        return table.get(lexeme).getAddress();
    }

    public Symbol searchSymbol(String lexeme) {
        return table.get(lexeme);
    }

    public Symbol insertId(String lexeme) {
        table.put(lexeme, new Symbol(lexeme, ID, ++add));
        return table.get(lexeme);
    }

    public Symbol insertConst(String lexeme, String type) {
        table.put(lexeme, new Symbol(lexeme, CONST, type, ++add));
        return table.get(lexeme);
    }

}
