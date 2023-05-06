package Interpreter;
/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
public class BooleanDataType extends InterpreterDataType{
    /**
     * the boolean value for this data type
     */
    private boolean value;

    private boolean changeable;

    /**
     * cinstructor for a BooleanDataTypes
     * @param input boolean value being inputted
     */
    public BooleanDataType(boolean input){
        this.value = input;
    }

    public BooleanDataType(boolean input, boolean chng){
        value = input;
        changeable = chng;
    }

    public boolean isChangeable(){
        return changeable;
    }

    /**
     * toString method for a BooleanDataType
     * @return either "true" or "false" depending on the boolean value
     */
    public String toString(){
        if(this.value){
            return "true";
        }
        else{
            return "false";
        }
    }

    /**
     * fromString method for a BooleanDataType
     * @param input string being inputted to be assigned to the boolean value
     */
    public void fromString(String input){
        this.value = Boolean.parseBoolean(input);
    }

    /**
     * getter method for the data being held in this object
     * @return the boolean value of this object as an Object
     */
    public Object getData() {
        return this.value;
    }

    /**
     * setter method for the data in this object
     * @param bool the boolean value to be set in this object
     */
    public void setData(Object bool){
        value = (boolean) bool;
    }
}
