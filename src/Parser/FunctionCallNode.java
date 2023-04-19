/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Parser;
import java.util.LinkedList;

public class FunctionCallNode extends StatementNode{
    /**
     * name of the function being called
     */
    private String functionName;
    /**
     * list of parameters listed with the function call
     */
    private LinkedList<ParameterNode> parametersList;

    /**
     * contructor for a FunctionCallNode
     * @param funcname name of the function being called
     * @param params list of function call parameters
     */
    public FunctionCallNode(String funcname, LinkedList<ParameterNode> params){
        this.functionName = funcname;
        this.parametersList = params;
    }

    public String getFunctionName(){
        return functionName;
    }

    public LinkedList<ParameterNode> getParametersList(){
        return parametersList;
    }

    /**
     * toString method for a FunctionCallNode
     * @return a String containing information about the function call, including the functions name and its parameters
     */
    public String toString(){
        String s = "FUNCTION CALL NODE:---------------------------\n";
        s += "function: " + functionName+ "\n";
        s += "PARAMETERS:-----------------------------\n";
        for(int i = 0; i < parametersList.size(); i++){
            s += parametersList.get(i) + ", \n";
        }
        s += "------------------------------------------";
        return s;
    }
}
