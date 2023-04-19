/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */

package Parser;
public class ParameterNode extends Node{
    /**
     * variable reference node for the case that this parameter is a var
     */
    private VariableReferenceNode varIdentifier;
    /**
     * node for the case that this parameter is any other shank expression
     */
    private Node paramExpression;

    private boolean isVar;

    /**
     * contructor for a paramter node, where the parameter is a var
     * @param var the var parameter
     */
    public ParameterNode(VariableReferenceNode var, boolean isVar){
        this.varIdentifier = var;
        this.paramExpression = null;
        this.isVar = isVar;
    }

    /**
     * contructor for a parameter node, where the parameter is any other shank expression
     * @param ex the node containing the expression
     */
    public ParameterNode(Node ex){
        this.varIdentifier = null;
        this.paramExpression = ex;
        this.isVar = false;
    }

    public VariableReferenceNode getVarIdentifier(){
        return varIdentifier;
    }

    public Node getParamExpression(){
        return paramExpression;
    }

    public boolean isVar(){
        return isVar;
    }

    /**
     * toString method for a paramter node
     * @return  a string containing the parameter that was parsed
     */
    public String toString(){
        if(this.paramExpression == null){
            return "ParameterNode(" + this.varIdentifier + ")";
        }
        else{
            return "ParamterNode(" + this.paramExpression +")";
        }
    }
}
