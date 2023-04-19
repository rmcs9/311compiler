/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Interpreter;
import java.util.LinkedList;

public class BuiltInStart extends BuiltIn{
    /**
     * getter method for the name of this function
     * @return "Start" as a string
     */
    public String getFunctionName(){
        return "Start";
    }

    /**
     * execute method for the Start function
     * @param parametersList the parameters being passed into the function
     */
    public void execute(LinkedList<InterpreterDataType> parametersList){
        //checks if the right amount of parameters are being passed in
        if(parametersList.size() != 2){
            throw new InterpreterRuntimeException("Start method must take 2 parameters");
        }
        //checks if the right type parameters are being passed in
        if(parametersList.get(0) instanceof ArrayDataType){
            if(parametersList.get(1) instanceof IntegerDataType){
                //sets the second parameter to the starting index of the 1st paramter array
                ((IntegerDataType) parametersList.get(1)).setData(((ArrayDataType) parametersList.get(0)).getStartingIndex());
            }else{throw new InterpreterRuntimeException("second parameter of Start method must be of type var integer");}
        }else{throw new InterpreterRuntimeException("first parameter of Start method must be of type Array");}
    }
    public boolean isVar(int paramNumber){
        return paramNumber == 1;
    }
    /**
     * tosString method for the Start function
     * @return a string containing information about the method including its parameters and what it does
     */
    public String toString(){
        return "function Start\nParameters: someArray, var someInt\nreturns someInt as the starting index of someArray";
    }
}
