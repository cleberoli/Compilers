public class Memory {
    
    private static int memory;
    private static int temporary;

    public Memory() {
        memory = 16384;
        temporary = 0;
    }
    
    public void resetTemporary() {
        temporary = 0;
    }
    
    public int allocateByte() {
        int address = memory;
        memory += 1;
        return address;
    }
    
    public int allocateInteger() {
        int address = memory;
        memory += 2;
        return address;
    }
    
    public int allocateLogical() {
        int address = memory;
        memory += 1;
        return address;
    }
    
    public int allocateString() {
        int address = memory;
        memory += 256;
        return address;
    }
    
    public int allocateString(int size) {
        int address = memory;
        memory += size;
        return address;
    }
    
    public int allocateTemporaryByte() {
        int address = temporary;
        temporary += 1;
        return address;
    }
    
    public int allocateTemporaryInteger() {
        int address = temporary;
        temporary += 2;
        return address;
    }
    
    public int allocateTemporaryLogical() {
        int address = temporary;
        temporary += 1;
        return address;
    }
    
    public int allocateTemporaryString() {
        int address = temporary;
        temporary += 256;
        return address;
    }
    
    public int allocateTemporaryString(int size) {
        int address = temporary;
        temporary += size;
        return address;
    }
    
    public int allocateTemporaryBuffer() {
        int address = temporary;
        temporary += 259;
        return address;
    }

    public static int getMemory() {
        return memory;
    }

    public static int getTemporary() {
        return temporary;
    }
    
    
}