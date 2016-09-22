/*
* Pontifical Catholic University of Minas Gerais
* Institue of Exact Sciences and Technology
* Compilers
*
* Authors: Cleber Oliveira, Karen Martins, and Sarah Almeida
* IDs: 486564, 476140, 476181
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Runs the compiler and tests the source files.
 * @author oli
 */
public class Compiler {

    /**
     * Reads from the standard input one number N, and then N strings containing
     * the relative paths to the source files.
     */
    public static void main(String[] args) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String path = "";
        int n;

        try {
            n = Integer.parseInt(input.readLine());
            
            for (int i = 0; i < n; i++) {
                path = input.readLine().trim();
                
                if(path.length() > 2) {
                    int size = path.length();
                    if(path.charAt(size - 2) == '.' && (path.charAt(size - 1) == 'l' || path.charAt(size - 1) == 'L' )) {
                        Parser p = new Parser(new BufferedReader(new FileReader(path)));
                        System.out.print ("["+ path + "] ");
                        p.S();
                        System.out.println("compilado com sucesso.");
                    } else {
                        System.out.println("["+ path + "] arquivo nao compativel.");
                    }
                } else {
                    System.out.println("["+ path + "] arquivo nao encontrado.");
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }

}