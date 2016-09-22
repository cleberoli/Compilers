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

    /**
     * Default constructor, it initializes the variables that will be used
     * during its execution, setting lexeme = "", token = 0, address = 0;
     */
    public Symbol() {
        this.lexeme = "";
        this.token = 0;
        this.address = 0;
    }

    /**
     * Constructor that will initialize the symbol with the given information.
     * @param lexeme String containing the written representation of the symbol in the source file.
     * @param token byte representing its function in the language.
     * @param address integer indicating the symbol's address.
     */
    public Symbol(String lexeme, byte token, int address) {
    this.lexeme = lexeme;
    this.token = token;
    this.address = address;
  }

    /**
     * Recovers the attribute lexeme of a symbol
     * @return String lexeme of a symbol
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * Changes the attribute lexeme of a symbol
     * @param lexeme new lexeme
     */
    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    /**
     * Recovers the attribute token of a symbol
     * @return byte token of a symbol
     */
    public byte getToken() {
        return token;
    }

    /**
     * Changes the attribute token of a symbol
     * @param token new token
     */
    public void setToken(byte token) {
        this.token = token;
    }

    /**
     * Recovers the attribute address of a symbol
     * @return integer address of a symbol
     */
    public int getAddress() {
        return address;
    }

    /**
     * Changes the attribute address of a symbol
     * @param address new address
     */
    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Symbol{" + "lexeme=" + lexeme + ", token=" + token + ", address=" + address + '}';
    }
    
}