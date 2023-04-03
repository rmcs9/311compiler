/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */

import java.util.LinkedList;
import java.util.Random;

public class BuiltInGetRandom extends FunctionNode{
    /**
     * getter method for the name of this function. used as a key for the programNode hashmap
     * @return "GetRandom" as a String
     */
    public String getFunctionName(){
        return "GetRandom";
    }

    /**
     * execute method for the GetRandom function
     * @param parameterList the parameters being passed into this function
     */
    public void execute(LinkedList<InterpreterDataType> parameterList){
       //checks if the right amount of parameters are being passed in
        if(parameterList.size() != 1){
            throw new InterpreterRuntimeException("GetRandom method takes 1 parameter only");
        }
        //checks if the correct type parameters are being passed in
        if(parameterList.get(0) instanceof IntegerDataType){
            //creates a new random number generator
            Random rand = new Random();
            //passes a new random integer into the first parameter
            ((IntegerDataType) parameterList.get(0)).setData(rand.nextInt());
        }else{
            throw new InterpreterRuntimeException("first parameter of GetRandom method must be of type int");
        }
    }

    /**
     * toString method for GetRandom function
     * @return a string with info about the function including its parameters and what the function does
     */
    public String toString(){
        return "function: GetRandom\nParameters: var randomInt\n randomInt returns as a random integer value";
    }
}
