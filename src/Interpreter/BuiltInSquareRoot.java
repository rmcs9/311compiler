/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Interpreter;
import java.util.LinkedList;

public class BuiltInSquareRoot extends BuiltIn{
    /**
     * getter method for the name of this function
     * @return "SquareRoot" as a String
     */
    public String getFunctionName(){
        return "SquareRoot";
    }

    /**
     * execute method for the SquareRoot function
     * @param parameterList the parameters being passed into the function
     */
    public void execute(LinkedList<InterpreterDataType> parameterList){
        //checks if the right amount of parameters are being passed in
        if(parameterList.size() != 2){
            throw new InterpreterRuntimeException("SquareRoot method must take only 1 parameter");
        }
        //checks if the correct type parameters are being passed in
        if(parameterList.get(0) instanceof RealDataType){
            if(parameterList.get(1) instanceof RealDataType){
                //takes the sqrt of the first parameter and sets it to the second parameter
                float val = (float)Math.sqrt((float)parameterList.get(0).getData());
                ((RealDataType) parameterList.get(1)).setData(val);
            }else{throw new InterpreterRuntimeException("second parameter in SquareRoot method must be of type var real");}
        }else{throw new InterpreterRuntimeException("first parameter in SquareRoot method must be of type real");}
    }
    public boolean isVar(int paramNumber){
        return paramNumber == 1;
    }
    /**
     * toString method for the SquareRoot function
     * @return a string containing information about the method including its parameters and what it does
     */
    public String toString(){
        return "function SquareRoot\nParameters: someReal, var sqrtReal\nreturns sqrtReal as the square root of someReal";
    }
}
