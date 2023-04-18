/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */

import java.util.LinkedList;
public class BuiltInLeft extends BuiltIn{
    /**
     * getter method for the name of this function. used as a key in the programNode hashmap
     * @return "Left" as a string
     */
    public String getFunctionName(){
        return "Left";
    }

    /**
     * execute method for the Left function
     * @param parameterList the parameters being passed into the function
     */
    public void execute(LinkedList<InterpreterDataType> parameterList){
        //checks if the correct amount of params are being passed in
        if(parameterList.size() != 3){
            throw new InterpreterRuntimeException("Left method must take 3 parameters");
        }
        //checks if the correct type params are being passed in
        if(parameterList.get(0) instanceof StringDataType){
            if(parameterList.get(1) instanceof IntegerDataType){
                if(parameterList.get(2) instanceof StringDataType){
                    //takes a substring of parameter 1 and sets it to the value of parameter 3
                    parameterList.get(2).fromString(parameterList.get(0).toString().substring(0, (int)parameterList.get(1).getData()));
                } else{throw new InterpreterRuntimeException("third parameter in Left method must be of type String");}
            } else{throw new InterpreterRuntimeException("second parameter in Left method must be of type integer");}
        } else{throw new InterpreterRuntimeException("first parameter in Left method must be of type String");}
    }

    public boolean isVar(int paramNumber){
        return paramNumber == 2;
    }
    /**
     * toString method for Left function
     * @return a string with info about the function including its parameter and what it does
     */
    public String toString(){
        return "function Left\nParameters: someString, intLength, var newString\nfirst length characters of someString";
    }
}
