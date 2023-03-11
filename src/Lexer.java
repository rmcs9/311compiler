/**
 * ICSI 311
 * Assignment 1
 * Ryan McSweeney
 * RM483514
 * 2/3/23
 */

import java.util.LinkedList;
import java.util.List;


public class Lexer {
    /**
     * linked list containing all lexed tokens
     */
    private static LinkedList<Token> tokens = new LinkedList<>();

    /**
     * lex method containing state machine "machine" used to lex incoming lines of text and
     * generate new tokens
     * @param line a String representing 1 line from a shank file
     */
    public static void lex(String line){
        State machine = new State();
        int i = 0;
        List<Character> input = new LinkedList<>();
        char[] arr = line.toCharArray();
        //for loop used to translate incoming line of text into a linked list of characters
        for(int j = 0; j < arr.length; j++){
            input.add(j, arr[j]);
        }
        //accumaluted word
        String accumulator = new String();

            while(i < input.size()) {
                if(!Character.isDigit(input.get(i)) && !Character.isLetter(input.get(i))
                    && input.get(i) != '.' && input.get(i) != ' ' ){
                    throw new LexerException("Unknown character detected, lexing process aborted.");
                }
                else {
                    switch (machine.getState()) {
                        //Start state of state machine
                        case "START":
                            if (Character.isLetter(input.get(i))) {
                                machine.setWord();
                            } else if (Character.isDigit(input.get(i))) {
                                machine.setNumber();
                            } else if (input.get(i) == '.') {
                                machine.setPeriod();
                            }
                            else if(input.get(i) == ' '){
                                machine.setStart();
                                i++;
                            }
                            break;
                        //Word state of state machine
                        case "WORD":
                            if (Character.isLetter(input.get(i)) || Character.isDigit(input.get(i))) {
                                accumulator += input.get(i);
                                i++;
                            } else if (input.get(i) == ' ' || input.get(i) == '.') {
                                tokens.add(new Token(Token.tokenType.WORD, accumulator.toString()));
                                accumulator = new String();
                                machine.setStart();
                                i++;
                            }
                            break;
                        //Number state of the state machine
                        case "NUMBER":
                            if (Character.isDigit(input.get(i))) {
                                accumulator += input.get(i);
                                i++;
                            } else if (input.get(i) == '.') {
                                machine.setPeriod();
                                accumulator += input.get(i);
                                i++;
                            } else {
                                tokens.add(new Token(Token.tokenType.NUMBER, accumulator.toString()));
                                accumulator = new String();
                                machine.setStart();
                                i++;
                            }
                            break;
                        //Period state of the state machine
                        case "PERIOD":
                            if (Character.isDigit(input.get(i))) {
                                accumulator += input.get(i);
                                i++;
                            } else {
                                tokens.add(new Token(Token.tokenType.NUMBER, accumulator.toString()));
                                accumulator = new String();
                                machine.setStart();
                                i++;
                            }
                            break;
                    }
                }
            }
            //checks if accumulator is empty before moving onto the next line in the shank file
            //if accumulator contains a string, it creates a new token for whatever is left in the accumulator
            if(!accumulator.isEmpty()){
                if(machine.getState() == "WORD"){
                    tokens.add(new Token(Token.tokenType.WORD, accumulator));
                    accumulator = new String();
                }
                else{
                    tokens.add(new Token(Token.tokenType.NUMBER, accumulator));
                    accumulator = new String();
                }
            }
            tokens.add(new Token(Token.tokenType.ENDOFLINE));
    }

    /**
     * method referenced in main that is used to print all the tokens contained in the tokens list
     */
    public static void tokenPrint(){
        for(int i = 0; i < tokens.size(); i++){
            System.out.println(tokens.get(i).toString());
        }
    }

}
