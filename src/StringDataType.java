/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
public class StringDataType extends InterpreterDataType{

    private String value;

    public StringDataType(String input){
        value = input;
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
