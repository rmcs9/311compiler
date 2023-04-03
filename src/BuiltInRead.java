/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */
import java.util.LinkedList;
import java.util.Scanner;


public class BuiltInRead extends FunctionNode{
    /**
     * getter method for the name of this function. used as a key for the programNode hashmap
     * @return "Read" as a string
     */
    public String getFunctionName(){
        return "Read";
    }

    /**
     * execute method for the Read function
     * @param paramList the parameters being passed into this function
     */
    public void execute(LinkedList<InterpreterDataType> paramList){
        //new scanned object created
        Scanner input = new Scanner(System.in);
        //input split into different elements by whitespace
        String[] array = input.nextLine().split(" ");
        try{
            //reads values into the parameters
            for(int i = 0; i < paramList.size(); i++){
                paramList.get(i).fromString(array[i]);
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            throw new InterpreterRuntimeException("too many user values inputted. Not enough vars entered into read");
        }
    }

    /**
     * this method is variadic
     * @return true
     */
    public boolean isVariadic(){
        return true;
    }

    /**
     * toString for read function
     * @return  a string conatining information about the read function including its parameters and what it does
     */
    public String toString(){
        return "function Read\nParameters: var a, var b, var c ...\nreads values inputted by the user into the provided variables";
    }
}


