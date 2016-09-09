

public class Symbol {
  private String lexeme;
  private byte token;
  private String type;
  private int address;

  public Symbol() {

  }

  public Symbol(String lexeme, byte token, int address) {
    this.lexeme = lexeme;
    this.token = token;
    this.type = "";
    this.address = address;
  }

  public Symbol(String lexeme, byte token, String type, int address) {
    this.lexeme = lexeme;
    this.token = token;
    this.type = type;
    this.address = address;
  }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public byte getToken() {
        return token;
    }

    public void setToken(byte token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Symbol{" + "lexeme=" + lexeme + ", token=" + token + ", type=" + type + ", address=" + address + '}';
    }
    
}
