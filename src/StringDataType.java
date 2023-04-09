/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
public class StringDataType extends InterpreterDataType{

    private String value;

    private boolean changeable = true;

    public StringDataType(String input){
        value = input;
    }

    public StringDataType(String input, boolean chng){
        value = input;
        changeable = chng;
    }

    public boolean isChangeable(){
        return changeable;
    }

    public String toString(){
        return this.value;
    }

    public void fromString(String input){
        this.value = input;
    }

    public Object getData(){
        return this.value;
    }
}
