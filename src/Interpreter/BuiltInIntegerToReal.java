/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Interpreter;
import java.util.LinkedList;
public class BuiltInIntegerToReal extends BuiltIn{
    /**
     * getter method for the name of this function. used as a key in the programNode hashmap
     * @return "IntegerToReal" as a String
     */
    public String getFunctionName(){
        return "IntegerToReal";
    }

    /**
     * execute method for the IntegerToReal method
     * @param parameterList the parameters being passed in to the method
     */
    public void execute(LinkedList<InterpreterDataType> parameterList){
       //checks if the right amount of parameters are being passed in
        if(parameterList.size() != 2){
            throw new InterpreterRuntimeException("IntegerToReal method takes 2 parameters");
        }
        //checks if the correct type parameters are being passed in
        if(parameterList.get(0) instanceof IntegerDataType){
            if(parameterList.get(1) instanceof RealDataType){
                //sets data in the second parameter to the real representation of the integer
                ((RealDataType) parameterList.get(1)).setData(Float.parseFloat(parameterList.get(0).toString()));
            }
            else{throw new InterpreterRuntimeException("second parameter in IntegerToReal method must be of type Real");}
        }
        else{throw new InterpreterRuntimeException("first parameter in IntegerToReal method must be of type int");}
    }

    public boolean isVar(int paramNumber){
        return paramNumber == 1;
    }

    /**
     * toString method for the IntegerToReal method
     * @return a string with information about the method including its parameters and what it does
     */
    public String toString(){
        return "function: IntegerToReal\nParameters: someInt, var realResult\nReturns realResult as the real version of someInt";
    }

}
