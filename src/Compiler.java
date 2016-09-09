
import java.io.BufferedReader;
import java.io.FileReader;

public class Compiler {

    public static void main(String[] args) throws Exception {
        LexicalAnalyzer lexical = new LexicalAnalyzer();
        BufferedReader file = new BufferedReader(new FileReader("test/exemplo.l"));

        while (true) {
            System.out.println("lex " + lexical.getNextLexeme(file));
        }
    }

}
