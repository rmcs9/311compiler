/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
package Interpreter;
public class IntegerDataType extends InterpreterDataType{

    private int value;

    private boolean changeable = true;

    /**
     * constructor for an integer data type
     * @param input integer being passed in
     */
    public IntegerDataType(int input){
        this.value = input;
    }

    public IntegerDataType(int input, boolean chng){
        value = input;
        changeable = chng;
    }

    public boolean isChangeable(){
        return changeable;
    }

    /**
     * toString method for IntegerDataType
     * @return the integer held in this object, as a String
     */
    public String toString(){
        return Integer.toString(this.value);
    }

    /**
     * fromString method
     * @param input string input being passed into the object
     */
    public void fromString(String input){
        this.value = Integer.parseInt(input);
    }

    /**
     * getter method for the data in this object
     * @return the data in this object as an Object
     */
    public Object getData(){
        return this.value;
    }

    /**
     * setter method for the data in this object
     * @param data integer being passed in
     */
    public void setData(Object data){
        value = (int) data;
    }
}
