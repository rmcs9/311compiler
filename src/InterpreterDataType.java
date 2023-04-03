/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
public abstract class InterpreterDataType {
    public abstract String toString();

    public abstract void fromString(String input);

    public abstract Object getData();

    public InterpreterDataType(){

    }
}
