package Interpreter;

/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */



public abstract class InterpreterDataType {
    public abstract String toString();

    public abstract void fromString(String input);

    public abstract Object getData();

    public abstract boolean isChangeable();

    public abstract void setData(Object data);

    public InterpreterDataType(){
    }
}
