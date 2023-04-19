/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
package Interpreter;
public class CharacterDataType extends InterpreterDataType{
    /**
     * char being held in this object
     */
    private char value;

    private boolean changeable = true;

    /**
     * constructor for a CharacterDataType
     * @param input char being passed in
     */
    public CharacterDataType(char input){
        this.value = input;
    }

    public CharacterDataType(char input, boolean chng){
        value = input;
        changeable = chng;
    }

    public boolean isChangeable(){
        return changeable;
    }

    /**
     * toString method for a CharacterDataType
     * @return the charcter in this object
     */
    public String toString(){
        return Character.toString(this.value);
    }

    /**
     * fromString method for a CharacterDataType
     * @param input being passed in
     */
    public void fromString(String input){
        this.value = input.charAt(0);
    }

    /**
     * getter method for the data in this object
     * @return an Object representing the data in this object
     */
    public Object getData(){
        return this.value;
    }


    /**
     * setter method for the data in this objec
     * @param d char being passed in
     */
    public void setData(char d){
        value = d;
    }
}
