
public class AuxiliarySymbol {
    
    public AuxiliarySymbol() {
        this.type = Byte.MAX_VALUE;
    }
    
    public AuxiliarySymbol(byte type) {
        this.type = type;
    }
   
    private byte type;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
    
}
