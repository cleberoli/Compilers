
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Enumeration;

public class Compiler {

    public static void main(String[] args) throws Exception {
        LexicalAnalyzer lexical = new LexicalAnalyzer();
        BufferedReader file = new BufferedReader(new FileReader("test/exemplo.l"));

        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
        System.out.println("lex " + lexical.getNextLexeme(file));
    }

}
