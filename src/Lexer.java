/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Lexer {
    /**
     * linked list containing all lexed tokens
     */
    private LinkedList<Token> tokens = new LinkedList<Token>();

    /**
     * variable that tracks what line the lexer is on in the file
     */
    private int lineCounter = 1;
    /**
     * integer that tracks the number of spaces/indents on the previous line
     */
    private int tabCounter = 0;
    /**
     * determines whether the lexer is in the middle of a multi line comment
     */
    private static boolean inComment;

    /**
     * lexer method lexes the incoming line in the file and adds all tokens to the linked list
     *
     * @param line String representing one line in the file
     */

    int previousIndent = 0;
    public void lex(String line) {
        if (!line.isEmpty()) {
            //hash map declaration
            HashMap<String, Token.tokenType> knownWords = new HashMap<String, Token.tokenType>();
            knownWords.put("while", Token.tokenType.WHILE);
            knownWords.put("for", Token.tokenType.FOR);
            knownWords.put("if", Token.tokenType.IF);
            knownWords.put("else", Token.tokenType.ELSE);
            knownWords.put("elsif", Token.tokenType.ELSIF);
            knownWords.put("define", Token.tokenType.DEFINE);
            knownWords.put("constants", Token.tokenType.CONSTANTS);
            knownWords.put("variables", Token.tokenType.VARIABLES);
            knownWords.put("array", Token.tokenType.ARRAY);
            knownWords.put("integer", Token.tokenType.INTEGER);
            knownWords.put("real", Token.tokenType.REAL);
            knownWords.put("boolean", Token.tokenType.BOOLEAN);
            knownWords.put("character", Token.tokenType.CHARACTERLITERAL);
            knownWords.put("string", Token.tokenType.STRINGLITERAL);
            knownWords.put("write", Token.tokenType.WRITE);
            knownWords.put("from", Token.tokenType.FROM);
            knownWords.put("to", Token.tokenType.TO);
            knownWords.put("var", Token.tokenType.VAR);
            knownWords.put("mod", Token.tokenType.MOD);
            knownWords.put("not", Token.tokenType.NOT);
            knownWords.put("and", Token.tokenType.AND);
            knownWords.put("or", Token.tokenType.OR);
            knownWords.put("then", Token.tokenType.THEN);
            knownWords.put("of", Token.tokenType.OF);
            knownWords.put("true", Token.tokenType.TRUE);
            knownWords.put("false", Token.tokenType.FALSE);

            State machine = new State();
            int i = 0;
            List<Character> input = new LinkedList<>();
            char[] arr = line.toCharArray();
            //for loop used to translate incoming line of text into a linked list of characters
            for (int j = 0; j < arr.length; j++) {
                input.add(j, arr[j]);
            }

            //accumaluted word
            String accumulator = new String();


            while (i < input.size()) {
                //checks if lexer is still inside a possible multiline comment
                if (inComment) {
                    machine.setComment();
                }
                int currentIndent = getIndent(line);
                for(int k = 0; k < currentIndent - previousIndent; k++){
                    tokens.add(new Token(Token.tokenType.INDENT, lineCounter));
                }
                for(int k = 0; k < previousIndent - currentIndent; k++){
                    tokens.add(new Token(Token.tokenType.DEDENT, lineCounter));
                }
                previousIndent = currentIndent;
                switch (machine.getState()) {
                    //Start state of state machine
                    case "START":
                        if (Character.isLetter(input.get(i))) {
                            machine.setWord();
                        } else if (Character.isDigit(input.get(i))) {
                            machine.setNumber();
                        } else if (input.get(i) == '.') {
                            machine.setPeriod();
                        } else if (input.get(i) == '{') {
                            machine.setComment();
                            i++;
                        } else if (input.get(i) == '(') {
                            machine.setLParan();
                        }else if(input.get(i) == ')'){
                            machine.setRPAREN();
                        }else if (input.get(i) == '[') {
                            machine.setLBRACKET();
                        }else if(input.get(i) == ']'){
                            machine.setRBRACKET();
                        }else if (input.get(i) == '"') {
                            machine.setStringLiteral();
                            i++;
                        } else if (input.get(i) == '\'') {
                            i++;
                            if(input.get(i + 1) != '\''){
                                throw new SyntaxErrorException(input.get(i + 1), lineCounter, "MORE THAN 1 CHARACTER INSIDE CHAR QUOTE");
                            }
                            else{
                            tokens.add(new Token(Token.tokenType.CHARACTERLITERAL, input.get(i).toString(), lineCounter));
                            i = i + 2;
                            machine.setStart();
                            }
                        } else if (input.get(i) == ' ') {
                            machine.setStart();
                            i++;
                        } else {
                            switch (input.get(i)) {
                                case '+':
                                    tokens.add(new Token(Token.tokenType.PLUS, lineCounter));
                                    i++;
                                    break;
                                case '-':
                                    tokens.add(new Token(Token.tokenType.MINUS, lineCounter));
                                    i++;
                                    break;
                                case '*':
                                    tokens.add(new Token(Token.tokenType.MULTIPLY, lineCounter));
                                    i++;
                                    break;
                                case '/':
                                    tokens.add(new Token(Token.tokenType.DIVIDE, lineCounter));
                                    i++;
                                    break;
                                case ':':
                                    if (input.get(i + 1) == '=') {
                                        tokens.add(new Token(Token.tokenType.ASSIGNMENT, lineCounter));
                                        i++;
                                    } else {
                                        tokens.add(new Token(Token.tokenType.COLON, lineCounter));
                                    }
                                    i++;
                                    break;
                                case ',':
                                    tokens.add(new Token(Token.tokenType.COMMA, lineCounter));
                                    i++;
                                    break;
                                case '=':
                                    tokens.add(new Token(Token.tokenType.EQUALS, lineCounter));
                                    i++;
                                    break;
                                case '<':
                                    if (input.get(i + 1) == '>') {
                                        tokens.add(new Token(Token.tokenType.NOTEQUALS, lineCounter));
                                        i++;
                                    } else if (input.get(i + 1) == '=') {
                                        tokens.add(new Token(Token.tokenType.LESSTHANEQUALTO, lineCounter));
                                        i++;
                                    } else {
                                        tokens.add(new Token(Token.tokenType.LESSTHAN, lineCounter));
                                    }
                                    i++;
                                    break;
                                case '>':
                                    if (input.get(i + 1) == '=') {
                                        tokens.add(new Token(Token.tokenType.GREATERTHANEQUALTO, lineCounter));
                                        i++;
                                    } else {
                                        tokens.add(new Token(Token.tokenType.GREATERTHAN, lineCounter));
                                    }
                                    i++;
                                    break;

                                case ';':
                                    tokens.add(new Token(Token.tokenType.SEMICOLON, lineCounter));
                                    i++;
                                    break;

                                default:
                                    throw new SyntaxErrorException(input.get(i), lineCounter);
                            }
                        }
                        break;
                    //Word state of state machine
                    case "WORD":
                        if (Character.isLetter(input.get(i)) || Character.isDigit(input.get(i))) {
                            accumulator += input.get(i);
                            i++;
                        } else {
                            if (knownWords.get(accumulator) != null) {
                                tokens.add(new Token(knownWords.get(accumulator), lineCounter));
                                accumulator = new String();
                                machine.setStart();

                            } else {
                                tokens.add(new Token(Token.tokenType.IDENTIFIER, accumulator.toString(), lineCounter));
                                accumulator = new String();
                                machine.setStart();

                            }
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
                            tokens.add(new Token(Token.tokenType.NUMBER, accumulator.toString(), lineCounter));
                            accumulator = new String();
                            machine.setStart();

                        }
                        break;
                    //Period state of the state machine
                    case "PERIOD":
                        if (Character.isDigit(input.get(i))) {
                            accumulator += input.get(i);
                            i++;
                        } else {
                            tokens.add(new Token(Token.tokenType.NUMBER, accumulator.toString(), lineCounter));
                            accumulator = new String();
                            machine.setStart();
                        }
                        break;
                    case "COMMENT":
                        inComment = true;
                        if (input.get(i) != '}') {
                            i++;
                        } else {
                            accumulator = new String();
                            machine.setStart();
                            i++;
                            inComment = false;
                        }
                        break;
                    case "LPAREN":
                       tokens.add(new Token(Token.tokenType.LPAREN, lineCounter));
                       machine.setStart();
                       i++;
                        break;
                    case "RPAREN":
                        tokens.add(new Token(Token.tokenType.RPAREN, lineCounter));
                        machine.setStart();
                        i++;
                        break;
                    case "LBRACKET":
                        tokens.add(new Token(Token.tokenType.LBRACKET, lineCounter));
                        machine.setStart();
                        i++;
                        break;
                    case "RBRACKET":
                        tokens.add(new Token(Token.tokenType.RBRACKET, lineCounter));
                        machine.setStart();
                        i++;
                        break;
                    case "STRINGLITERAL":
                        if (input.get(i) != '"') {
                            accumulator += input.get(i);
                            i++;
                        } else {
                            tokens.add(new Token(Token.tokenType.STRINGLITERAL, accumulator, lineCounter));
                            accumulator = new String();
                            machine.setStart();
                            i++;
                        }
                        break;
                }
            }
            //checks if accumulator is empty before moving onto the next line in the shank file
            //if accumulator contains a string, it creates a new token for whatever is left in the accumulator
            if (!accumulator.isEmpty()) {
                if (machine.getState() == "WORD") {
                    if (knownWords.get(accumulator) != null) {
                        tokens.add(new Token(knownWords.get(accumulator), lineCounter));
                        accumulator = new String();
                    } else {
                        tokens.add(new Token(Token.tokenType.IDENTIFIER, accumulator.toString(), lineCounter));
                        accumulator = new String();
                    }
                }
                else if(machine.getState() == "STRINGLITERAL" || machine.getState() == "CHARLITERAL"){
                    throw new SyntaxErrorException("unterminated " + machine.getState() + " detected on line " + lineCounter);
                }
                else {
                    tokens.add(new Token(Token.tokenType.NUMBER, accumulator, lineCounter));
                    accumulator = new String();
                }
            }
            if(!tokens.isEmpty()) {
                tokens.add(new Token(Token.tokenType.ENDOFLINE, lineCounter));
            }
            lineCounter++;
        }
        else {
            lineCounter++;
        }
    }

    /**
     * method referenced in main that is used to print all the tokens contained in the tokens list
     */
    public void tokenPrint() {
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i).toString());
        }
    }

    /**
     * toString method for lexer class
     *
     * @return a string represinting all the tokens on the line
     */
    public String toString() {
        String s = new String();
        for (int i = 0; i < tokens.size() - 1; i++) {
            s += tokens.get(i) + " ";
        }
        return s;
    }

    public LinkedList<Token> getTokens(){
        return this.tokens;
    }

    public int getIndent(String line){
        int i = 0;
        for(char j: line.toCharArray()){
            if(j == ' '){
                i++;
            }
            else if(j == '\t'){
                i += 4;
            }
            else{
                return i/4;
            }

        }
        return i/4;
    }


}
