/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
public class RealDataType extends InterpreterDataType{

    private float value;

    /**
     * constructor for this object
     * @param input the float being passed in
     */
    public RealDataType(float input){
        this.value = input;
    }

    public String toString(){
        return Float.toString(value);
    }

    public void fromString(String input){
        this.value = Float.parseFloat(input);
    }

    public Object getData(){
        return this.value;
    }

    public void setData(float data){
        value = data;
    }
}
