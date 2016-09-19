
import java.io.BufferedReader;
import java.io.FileReader;

public class Compiler {

    public static void main(String[] args) {
        try {
            Parser p = new Parser(new BufferedReader(new FileReader("test/prog3.l")));
            p.S();
            System.out.println("ok");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}