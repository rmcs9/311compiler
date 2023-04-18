/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */

import java.util.LinkedList;
public class BuiltInEnd extends BuiltIn{
    /**
     * getter method for the name of this function. Used as a key for the programNode hashmap
     * @return "End" as a String
     */
    public String getFunctionName(){
        return "End";
    }

    /**
     * execute method for the End method
     * @param parametersList LinkedList of the parameters being passed into this method
     */
    public void execute(LinkedList<InterpreterDataType> parametersList){
        //checks if the correct amount of params were passed in
        if(parametersList.size() != 2){
            throw new InterpreterRuntimeException("Array End method must have 2 parameters (array, var)");
        }
        //checks if the correct type of params were passed in
        if(parametersList.get(0) instanceof ArrayDataType){
            if(parametersList.get(1) instanceof IntegerDataType){
                //sets the value of param 2 to the ending index
                parametersList.get(1).fromString((String.valueOf(((ArrayDataType) parametersList.get(0)).getEndingIndex())));
            }
            else{throw new InterpreterRuntimeException("second parameter in array end method must be a var of type integer");}
        }
        else{throw new InterpreterRuntimeException("first parameter in array end method must be of type array");}
    }

    public boolean isVar(int paramNumber){
        return paramNumber == 1;
    }

    /**
     * toString for End function
     * @return a string with info about the function including parameters and what it does
     */
    public String toString(){
        return "function: End\nParameters: someArray, var intResult\nreturns the end index of someArray\n";
    }
}
