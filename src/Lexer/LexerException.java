/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

package Lexer;
public class LexerException extends RuntimeException{
    /**
     * constructer for a lexer exception object
     * @param str custom message for the exception
     */
    public LexerException(String str){
        super(str);
    }
}
