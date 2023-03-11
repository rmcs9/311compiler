/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

import java.util.LinkedList;

public class FunctionNode extends Node{
    /**
     * name of the function
     */
    private String functionName;
    /**
     * list of parameters contained in the function
     */
    LinkedList<VariableNode> parameters;
    /**
     * list of variables listed under the function
     */
    LinkedList<VariableNode> variables;
    /**
     * list of statements executed by the function
     */
    LinkedList<Node> statements;

    /**
     * empty constucter for function node
     */
    public FunctionNode(){}

    /**
     * constructer for function node
     * @param name name of the function
     * @param parameters list of function parameters
     * @param variables list of local function vraiables
     */
    public FunctionNode(String name, LinkedList<VariableNode> parameters, LinkedList<VariableNode> variables){
        this.functionName = name;
        this.parameters = parameters;
        this.variables = variables;
    }

    /**
     * setter method for function name
     * @param s function name
     */
    public void setFunctionName(String s){
        this.functionName = s;
    }

    /**
     * setter function for a single parameter
     * @param param one single parameter
     */
    public void addParam(VariableNode param){
        this.parameters.add(param);
    }

    /**
     * setter function for a single variable or constant
     * @param var one single variable or constant
     */
    public void addVariable(VariableNode var){
        variables.add(var);
    }

    /**
     * setter function for a single statement
     * @param statement one statement line
     */
    public void addStatement(StatementNode statement){
        statements.add(statement);
    }

    /**
     * getter function for function name
     * @return the name of the function
     */
    public String getFunctionName(){
        return this.functionName;
    }

    /**
     * to String method for a function Node
     * @return a string listing, the name of the function, the parameters and the variables, statements coming soon
     */
    public String toString() {
        String s = "function: " + this.functionName + "\nparameters: ";

        while(!this.parameters.isEmpty()){
            s += "\n" + this.parameters.remove();
        }

        s += "\nvariables: ";

        while(!this.variables.isEmpty()){
            s += "\n" + this.variables.remove();
        }


        return s;

    }
}
