
import java.io.BufferedReader;
import java.io.FileReader;

public class Compiler {

    public static void main(String[] args) {
        try {
            Parser p = new Parser(new BufferedReader(new FileReader("test/exemplo.l")));
            p.S();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}