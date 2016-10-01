/*
* Pontifical Catholic University of Minas Gerais
* Institue of Exact Sciences and Technology
* Compilers
*
* Authors: Cleber Oliveira, Karen Martins, and Sarah Almeida
* IDs: 486564, 476140, 476181
 */

/**
 * Entity class responsible for storing the symbol's information.
 */
public class Symbol {

    private String lexeme;
    private byte token;
    private int address;
    private byte category;
    private byte type;
    
    public static final byte NO_CATEGORY = 0;
    public static final byte CATEGORY_VARIABLE = 1;
    public static final byte CATEGORY_CONSTANT = 2;

    public static final byte NO_TYPE = 3;
    public static final byte TYPE_INTEGER = 4;
    public static final byte TYPE_LOGICAL = 5;
    public static final byte TYPE_STRING = 6;
    public static final byte TYPE_BYTE = 7;


    /**
     * Constructor that will initialize the symbol with the given information.
     *
     * @param lexeme String containing the written representation of the symbol
     * in the source file.
     * @param token byte representing its function in the language.
     */
    public Symbol(String lexeme, byte token) {
        this.lexeme = lexeme;
        this.token = token;
        this.address = -1;
        this.category = NO_CATEGORY;
        this.type = NO_TYPE;
    }

    /**
     * Recovers the attribute lexeme of a symbol
     *
     * @return String lexeme of a symbol
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * Changes the attribute lexeme of a symbol
     *
     * @param lexeme new lexeme
     */
    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    /**
     * Recovers the attribute token of a symbol
     *
     * @return byte token of a symbol
     */
    public byte getToken() {
        return token;
    }

    /**
     * Changes the attribute token of a symbol
     *
     * @param token new token
     */
    public void setToken(byte token) {
        this.token = token;
    }

    /**
     * Recovers the attribute address of a symbol
     *
     * @return integer address of a symbol
     */
    public int getAddress() {
        return address;
    }

    /**
     * Changes the attribute address of a symbol
     *
     * @param address new address
     */
    public void setAddress(int address) {
        this.address = address;
    }

    /**
     * Recovers the attribute category of a symbol
     *
     * @return byte category of a symbol
     */
    public byte getCategory() {
        return category;
    }

    /**
     * Changes the attribute category of a symbol
     *
     * @param category new category
     */
    public void setCategory(byte category) {
        this.category = category;
    }

    /**
     * Recovers the attribute type of a symbol
     *
     * @return String type of a symbol
     */
    public byte getType() {
        return type;
    }

    /**
     * Changes the attribute type of a symbol
     *
     * @param type new type
     */
    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Symbol{" + "lexeme=" + lexeme + ", token=" + token + ", address=" + address + ", category=" + category + ", type=" + type + '}';
    }

}
