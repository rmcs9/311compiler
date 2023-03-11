/**
 * ICSI 311
 * Assignment 1
 * Ryan McSweeney
 * RM483514
 * 2/3/23
 */


public class Token {
    /**
     * Constructer for a token
     * @param type enum for the type of token that is being created
     * @param contents String content of the token
     */
    public Token(tokenType type, String contents){
        this.tokenContents = contents;
        this.tokType = type;
    }

    /**
     * additional constructer for a token used specifically for ENDOFLINE tokens
     * @param type enum of token being created
     */
    public Token(tokenType type){
        this.tokType = type;
    }

    /**
     * enumerator for tokens
     */
    public enum tokenType{
        WORD, NUMBER, ENDOFLINE
    }

    /**
     * toString method for tokens
     * @return a String matching the "TOKENTYPE (CONTENT)" format
     */
    public String toString(){
        if(this.tokType == tokenType.WORD){
            return "WORD (" + tokenContents + ")";
        }
        else if(this.tokType == tokenType.NUMBER){
            return "NUMBER (" + tokenContents + ")";
        }
        else{
            return "ENDOFLINE";
        }
    }

    /**
     * String of the content in the token
     */
    private String tokenContents;
    /**
     * enum type of the token
     */
    private tokenType tokType;
}
