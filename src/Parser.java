
import java.io.BufferedReader;

public class Parser {

    BufferedReader file;
    LexicalAnalyzer lexical;
    SymbolTable table;
    Symbol s;

    public Parser(BufferedReader source) {
        try {
            file = source;
            lexical = new LexicalAnalyzer();
            table = new SymbolTable();
            s = lexical.getNextLexeme(file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    void matchToken(byte token) {
        try {
            if (s.getToken() == token) {
                s = lexical.getNextLexeme(file);
            } else {
                System.err.println(lexical.line + ":fim de arquivo nao esperado.");
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    void S() throws Exception {
        while (!lexical.EOF) {
            System.out.println("lex " + lexical.getNextLexeme(file));
        }
    }
}
