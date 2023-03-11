

/**
 * ICSI 311
 * Assignment 1
 * Ryan McSweeney
 * RM483514
 * 2/3/23
 */


public class LexerException extends RuntimeException{
    /**
     * constructer for a lexer exception object
     * @param str custom message for the exception
     */
    public LexerException(String str){
        super(str);
    }
}
