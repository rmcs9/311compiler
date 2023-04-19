package Interpreter;
/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
import Parser.*;
public class ArrayDataType extends InterpreterDataType{
    /**
     * the starting index of the array
     */
    private int startingIndex;
    /**
     * the ending index of the array
     */
    private int endingIndex;
    /**
     * the actual array
     */
    private InterpreterDataType[] array;

    /**
     * getter method for the array
     * @return the array contained in this variable
     */
    public InterpreterDataType[] getArray(){
        return this.array;
    }

    /**
     * ArrayDataType constructor that takes a variableNode as an input
     * @param var the variable node from the ADT tree
     */
    public ArrayDataType(VariableNode var){
        startingIndex = var.getFrom();
        endingIndex = var.getTo();
    }

    public ArrayDataType(InterpreterDataType[] arr, int from, int to){
        array = arr;
        startingIndex = from;
        endingIndex = to;
    }

    /**
     * ArrayDataType constructor that takes an IDT array as an input
     * @param arr the IDT array
     */
    public ArrayDataType(InterpreterDataType[] arr){
        array = arr;
    }

    /**
     * toString for ArrayDataType
     * @return the values contained in this array, concated together in one string seperated by a comma
     */
    public String toString(){
        String s = "";
        for(int i = 0; i < array.length; i++){
            s += array[i].toString() + ", ";
        }
        return s;
    }

    /**
     * fromString method takes an input and assigns it into the array
     * @param input string input being assigned into the array
     */
    public void fromString(String input){
        String[] s = input.split(",");
        for(int i = 0; i < s.length; i++){
            array[i].fromString(s[i]);
        }
    }

    /**
     *getter method for the starting index of this array
     * @return an int representing the starting index of this array
     */
    public int getStartingIndex(){
        return startingIndex;
    }

    /**
     * getter method for the ending index of this array
     * @return an int representing the ending index of this array
     */
    public int getEndingIndex(){
        return endingIndex;
    }

    /**
     * getter method for the data in this class
     * @return the array this class is holding, as an Object
     */
    public Object getData() {
        return this.array;
    }

    public boolean isChangeable(){
        return true;
    }

}
