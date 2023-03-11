/**
 * ICSI 311
 * Assignment 1
 * Ryan McSweeney
 * RM483514
 * 2/3/23
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Shank {
    public static void main(String[] args) throws IOException {
        //determines if there is an issue with the arguments passed into main
        if(args.length > 1){
            throw new RuntimeException("more than one argument provided");
        }
        else if(args.length < 1){
            throw new RuntimeException("no arguments provided");
        }
        else {
            Path myPath = Paths.get(args[0]);
            List<String> lines = Files.readAllLines(myPath, StandardCharsets.UTF_8);
            try {
                for (int i = 0; i < lines.size(); i++) {
                    Lexer.lex(lines.get(i));
                }
            } catch (Exception e) {
                System.out.println(e);
                Lexer.tokenPrint();
                System.exit(0);
            }

        }
        System.out.println("Lexing Successful!");
        Lexer.tokenPrint();



    }
}
