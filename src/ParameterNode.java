/**
 * ICSI 311
 * Assignment 6
 * Ryan McSweeney
 * RM483514
 * 3/26/23
 */
public class ParameterNode extends Node{
    /**
     * variable reference node for the case that this parameter is a var
     */
    VariableReferenceNode varIdentifier;
    /**
     * node for the case that this parameter is any other shank expression
     */
    Node paramExpression;

    /**
     * contructor for a paramter node, where the parameter is a var
     * @param var the var parameter
     */
    public ParameterNode(VariableReferenceNode var){
        this.varIdentifier = var;
        this.paramExpression = null;
    }

    /**
     * contructor for a parameter node, where the parameter is any other shank expression
     * @param ex the node containing the expression
     */
    public ParameterNode(Node ex){
        this.varIdentifier = null;
        this.paramExpression = ex;
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
