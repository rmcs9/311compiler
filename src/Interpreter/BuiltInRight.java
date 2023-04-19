/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Interpreter;
import java.util.LinkedList;

public class BuiltInRight extends BuiltIn{
    /**
     * getter method for the name of this function. used as a key in the programNode hashmap
     * @return "Right" as a String
     */
    public String getFunctionName(){
        return "Right";
    }

    /**
     * execute method for the Right function
     * @param parameterList the parameters being passed into the function
     */
    public void execute(LinkedList<InterpreterDataType> parameterList){
        //checks if the right amount of parameters are passed in
        if(parameterList.size() != 3){
            throw new InterpreterRuntimeException("Right function must take 3 parameters");
        }
        //checks if the right type parameters are passed in
        if(parameterList.get(0) instanceof StringDataType){
            if(parameterList.get(1) instanceof IntegerDataType){
                if(parameterList.get(2) instanceof StringDataType){
                    //sets the data in the 3rd parameter to the right substring of the 1st parameter
                    String s = parameterList.get(0).toString();
                    s = s.substring(s.length() - (int)parameterList.get(1).getData());
                    parameterList.get(2).fromString(s);
                }else{throw new InterpreterRuntimeException("third parameter of Right method must be a var string");}
            }else{throw new InterpreterRuntimeException("second paramter of Right method must be of type integer");}
        }else{throw new InterpreterRuntimeException("first parameter of Right method must be of type string");}
    }
    public boolean isVar(int paramNumber){
        return paramNumber == 2;
    }
    /**
     * toString method for Right function
     * @return a string containing information about the function including its parameters and what it does
     */
    public String toString(){
        return "function Right\nParameters: someString, someInt, var newString\nreturns newString as last length characters of someString";
    }
}
