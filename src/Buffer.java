
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Buffer {
    
    public ArrayList<String> buffer;
    
    public Buffer() {
        buffer = new ArrayList<>();
    }
    
    public void print(String file) {
        try {
            BufferedWriter _file = new BufferedWriter(new FileWriter(file));
            
            for (String s : buffer) {
                _file.write(s);
                _file.newLine();
            }
            
            _file.close();
             
        } catch(Exception e) {}
    }
    
    public void add(String str) {
        buffer.add(str);
    }
}
