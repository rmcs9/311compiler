/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Interpreter;
import java.util.LinkedList;

public class BuiltInSubstring extends BuiltIn{
    /**
     * getter method for the name of this method
     * @return "SubString" as a string
     */
    public String getFunctionName(){
        return "Substring";
    }

    /**
     * execute method for the Substring method
     * @param parametersList the parameters being passed into the method
     */
    public void execute(LinkedList<InterpreterDataType> parametersList){
        //checks if the correct amount of parameters are being passed in
        if(parametersList.size() != 4){
            throw new InterpreterRuntimeException("Substring method requires 4 parameters (String, int, int, var)");
        }
        //checks if the correct type parameters are being passed in
        if(parametersList.get(0) instanceof StringDataType){
            if(parametersList.get(1) instanceof IntegerDataType){
                if(parametersList.get(2) instanceof IntegerDataType){
                    if(parametersList.get(3) instanceof StringDataType){
                        //sets the 4th parameter as the substring of the 1st parameter between the 2 indexes provided
                        String s = parametersList.get(0).toString().substring(Integer.parseInt(parametersList.get(1).toString()), Integer.parseInt(parametersList.get(2).toString()));
                        parametersList.get(3).fromString(s);
                    }
                    else{throw new InterpreterRuntimeException("4th parameter of Substring must be of type string");}
                }
                else{throw new InterpreterRuntimeException("3rd parameter of Substring must be of type integer");}
            }
            else{throw new InterpreterRuntimeException("2nd parameter of Substring must be of type integer");}
        }
        else{throw new InterpreterRuntimeException("first parameter of Substring must be of type String");}
    }

    public boolean isVar(int paramNumber){
        return paramNumber == 3;
    }
    /**
     * toString method for substring function
     * @return a string containing information about the function including its parameters and what it does
     */
    public String toString(){
        return "function SubString\nParameters: someString, intStart, intEnd, var newString\nreturns newString as the substring from " +
                "intStart to intEnd";
    }
}
