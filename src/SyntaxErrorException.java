/**
 * ICSI 311
 * Assignment 2
 * Ryan McSweeney
 * RM483514
 * 2/12/23
 */
public class SyntaxErrorException extends RuntimeException{
    /**
     * Syntax error exception
     * @param unexpectedChar the character that was unexpected on the line
     * @param line the line that the exception was found on
     */
    public SyntaxErrorException(char unexpectedChar, int line){
        System.out.println(this.toString(unexpectedChar, line));
        System.exit(0);
    }

    /**
     * Syntax error exception
     * @param unexpectedChar the character that was unexpected on the line
     * @param line the line that the exception was found on
     * @param custommsg a custom message containing more information about why the excpetion was thrown
     */
    public SyntaxErrorException(char unexpectedChar, int line, String custommsg){
        System.out.println(this.toString(unexpectedChar, line));
        System.out.println(custommsg);
        System.exit(0);
    }

    /**
     * Syntax error exception with only a custom message
     * @param custommsg a custom message containing information about why the exception was thrown
     */
    public SyntaxErrorException(String custommsg){
        System.out.println(custommsg);
        System.exit(0);
    }

    /**
     * to String method for the exception
     * @param unexpectedChar the unexpected character in the program
     * @param line line the unexpected character was found on
     * @return a string with a custom error message
     */
    public String toString(char unexpectedChar, int line){
        return "unexpected character " + unexpectedChar + " found in line " + line;
    }
}
