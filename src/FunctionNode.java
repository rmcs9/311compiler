/**
 * ICSI 311
 * Assignment 6
 * Ryan McSweeney
 * RM483514
 * 3/26/23
 */

import javax.lang.model.element.VariableElement;
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
    LinkedList<StatementNode> statements;

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
    public FunctionNode(String name, LinkedList<VariableNode> parameters, LinkedList<VariableNode> variables, LinkedList<StatementNode> statements){
        this.functionName = name;
        this.parameters = parameters;
        this.variables = variables;
        this.statements = statements;
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

    public LinkedList<VariableNode> getVariables(){
        return variables;
    }

    public LinkedList<StatementNode> getStatements(){
        return statements;
    }

    public boolean isVariadic(){
        return false;
    }

    /**
     * to String method for a function Node
     * @return a string listing, the name of the function, the parameters and the variables, statements coming soon
     */
    public String toString() {
        String s = "function: " + this.functionName + "\nPARAMETERS: ------------------------------------------------------------";

        for(int i = 0; i < this.parameters.size(); i++){
            s += "\n" + this.parameters.get(i);
        }

        s += "\nVARIABLES: ------------------------------------------------------------";

        for(int i = 0; i < this.variables.size(); i++){
            s += "\n" + this.variables.get(i);
        }

        s += "\nSTATEMENTS: ------------------------------------------------------------";
        s += "\n";

        for(int i = 0; i < this.statements.size(); i++){
            s += "\n" + this.statements.get(i);
            s += "\n";
        }
        return s;

    }
}
