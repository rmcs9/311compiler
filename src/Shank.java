/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        System.out.println("Lexing Successful!");
        lex.tokenPrint();

        Parser parser = new Parser(lex.getTokens());
        System.out.println("Parse input successful");
        Node test = parser.parse();
        // I/O functions
        ((ProgramNode) test).addFunction(new BuiltInRead());
        ((ProgramNode) test).addFunction(new BuiltInWrite());
        //String functions
        ((ProgramNode) test).addFunction(new BuiltInLeft());
        ((ProgramNode) test).addFunction(new BuiltInRight());
        ((ProgramNode) test).addFunction(new BuiltInSubstring());
        //Number functions
        ((ProgramNode) test).addFunction(new BuiltInSquareRoot());
        ((ProgramNode) test).addFunction(new BuiltInGetRandom());
        ((ProgramNode) test).addFunction(new BuiltInIntegerToReal());
        ((ProgramNode) test).addFunction(new BuiltInRealToInteger());
        //Array functions
        ((ProgramNode) test).addFunction(new BuiltInStart());
        ((ProgramNode) test).addFunction(new BuiltInEnd());

        System.out.printf(test.toString());
        //semantic analysis call
        SemanticAnalysis sem = new SemanticAnalysis((ProgramNode) test);
        //interpreter is called and start method is interpreted
        Interpreter interpreter = new Interpreter((ProgramNode) test);
        interpreter.interpretFunction(((ProgramNode) test).getFunction("start"), null);
    }
}
