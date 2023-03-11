/**
 * ICSI 311
 * Assignment 2
 * Ryan McSweeney
 * RM483514
 * 2/12/23
 */


public class Token {

    public Token(){
    }
    /**
     * Constructer for a token
     * @param type enum for the type of token that is being created
     * @param contents String content of the token
     */
    public Token(tokenType type, String contents, int line){
        this.tokenContents = contents;
        this.tokType = type;
        this.tokenLine = line;
    }

    /**
     * additional constructer for a token used specifically for ENDOFLINE tokens
     * @param type enum of token being created
     */
    public Token(tokenType type, int line){
        this.tokType = type;
        this.tokenLine = line;
    }

    /**
     * enumerator for tokens
     */
    public enum tokenType{
        IDENTIFIER, NUMBER, ENDOFLINE, WHILE, FOR, IF, ELSE, ELSIF, DEFINE, CONSTANTS, VARIABLES, ARRAY, INTEGER, REAL,
        BOOLEAN, CHARACTER, STRING, WRITE, FROM, TO, VAR, MOD, NOT, AND, OR, COMMENT, PLUS, MINUS, MULTIPLY, DIVIDE, COLON, COMMA, ASSIGNMENT,
        EQUALS, NOTEQUALS, GREATERTHAN, LESSTHAN, GREATERTHANEQUALTO, LESSTHANEQUALTO, INDENT, DEDENT, THEN, SEMICOLON, LPAREN, RPAREN, LBRACKET, RBRACKET

    }

    /**
     * toString method for tokens
     * @return a String matching the "TOKENTYPE (CONTENT)" format
     */
    public String toString(){

        if(this.tokenContents == null){
            return this.tokType.toString();
        }
        else{
            return this.tokType.toString() + "(" + this.tokenContents + ")";
        }
    }

    public tokenType getTokenType(){
        return this.tokType;
    }

    public String getTokenContents(){
        return this.tokenContents;
    }

    /**
     * String of the content in the token
     */
    private String tokenContents;
    /**
     * enum type of the token
     */
    private tokenType tokType;

    private int tokenLine;
}
