/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
import Lexer.*;
import Parser.*;
import Interpreter.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Shank {
    public static void main(String[] args) throws IOException {
        //determines if there is an issue with the arguments passed into main
        Lexer lex;
        if (args.length > 1) {
            throw new RuntimeException("more than one argument provided");
        } else if (args.length < 1) {
            throw new RuntimeException("no arguments provided");
        } else {
            Path myPath = Paths.get(args[0]);
            List<String> lines = Files.readAllLines(myPath, StandardCharsets.UTF_8);
            lex = new Lexer();
            try {
                for (int i = 0; i < lines.size(); i++) {
                    lex.lex(lines.get(i));
                }
            } catch (Exception e) {
                System.out.println(e);
                lex.tokenPrint();
                System.exit(0);
            }

        }

        Scanner debugPrompter = new Scanner(System.in);
        System.out.println("DEBUG INFO: Would you like to print Lexers output to console? (y/n)");
        String answer = debugPrompter.next();
        if(answer.toCharArray()[0] == 'y' || answer.toCharArray()[0] == 'Y'){
            lex.tokenPrint();
        }

        Parser parser = new Parser(lex.getTokens());

        ProgramNode test = parser.parse();
        // I/O functions
        test.addFunction(new BuiltInRead());
        test.addFunction(new BuiltInWrite());
        //String functions
        test.addFunction(new BuiltInLeft());
        test.addFunction(new BuiltInRight());
        test.addFunction(new BuiltInSubstring());
        //Number functions
        test.addFunction(new BuiltInSquareRoot());
        test.addFunction(new BuiltInGetRandom());
        test.addFunction(new BuiltInIntegerToReal());
        test.addFunction(new BuiltInRealToInteger());
        //Array functions
        test.addFunction(new BuiltInStart());
        test.addFunction(new BuiltInEnd());

        System.out.println("DEBUG INFO: Would you like to print Parsers output to console? (y/n)");
        answer = debugPrompter.next();
        if(answer.toCharArray()[0] == 'y' || answer.toCharArray()[0] == 'Y'){
            System.out.printf(test.toString());
        }

        //semantic analysis call
        SemanticAnalysis sem = new SemanticAnalysis(test);
        //interpreter is called and start method is interpreted
        Interpreter interpreter = new Interpreter(test);
        interpreter.interpretFunction(test.getFunction("start"), null);
    }
}
