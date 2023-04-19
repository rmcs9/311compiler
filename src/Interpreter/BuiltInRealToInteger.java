/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */

package Interpreter;
import java.util.LinkedList;

public class BuiltInRealToInteger extends BuiltIn{
    /**
     * getter method for the name of this function. used as a key in the programNode hashmap
     * @return "RealToInteger" as a string
     */
    public String getFunctionName(){
            return "RealToInteger";
        }

    /**
     * execute method for the RealToInteger method
     * @param parameterList parameters being passed into the method
     */
    public void execute(LinkedList<InterpreterDataType> parameterList){
        //checks if the right amount of parameters are being passed in
        if(parameterList.size() != 2){
            throw new InterpreterRuntimeException("RealToInteger method can only take 2 parameters");
        }
        //checks if the right type prameters are being passed in
        if(parameterList.get(0) instanceof RealDataType){
            if(parameterList.get(1) instanceof IntegerDataType){
                //sets the second param to the integer representation of the 1st param
                ((IntegerDataType) parameterList.get(1)).setData((int)((float)parameterList.get(0).getData()));
            }else{throw new InterpreterRuntimeException("second parameter in RealToInteger method must be a var integer");}
        }else{throw new InterpreterRuntimeException("first parameter in RealToInteger method must be of type real");}
    }
    public boolean isVar(int paramNumber){
        return paramNumber == 1;
    }
    /**
     * toString method for RealToInteger method
     * @return information about the method including params and what it does
     */
    public String toString(){
        return "function RealToInteger\nParameters: someReal, var someInt\nreturns someInt as the integer truncated version of someReal";
    }

}
