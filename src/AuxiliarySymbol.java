public class AuxiliarySymbol {
    
    private byte type;
    private int address;
    
    public AuxiliarySymbol() {
        this.type = Byte.MAX_VALUE;
    }
    
    public AuxiliarySymbol(byte type, int address) {
        this.type = type;
        this.address = address;
    }
   
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
    
}
