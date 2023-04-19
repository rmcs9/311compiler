/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Parser;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class ProgramNode extends Node{
    /**
     * hash map for the program
     */
    private final HashMap<String, FunctionNode> functions;
    /**
     * linked list containing function names in the program
     */
    private final LinkedList<String> functionNames = new LinkedList<String>();

    /**
     * constructor for a new program node
     */
    public ProgramNode(){
        this.functions = new HashMap<String, FunctionNode>();
    }

    /**
     * method that adds a function to the program node
     * @param func function being added to the program node
     */
    public void addFunction(FunctionNode func){
        functionNames.add(func.getFunctionName());
        functions.put(func.getFunctionName(), func);
    }

    public FunctionNode getFunction(String key){
        return functions.get(key);
    }

    public LinkedList<String> getKeys(){
        return functionNames;
    }

    /**
     * to string method for the program node
     * @return a string representation of the functions inside of the program node
     */
    public String toString() {
        String s = "\nFunctions: \n";

        for(int i = 0; i < functionNames.size(); i++){
            s += "\n" + functions.get(functionNames.get(i));

            s += "\n";
        }

        return s;
    }
}
