/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

public class VariableNode extends Node{

    private String variableName;

    private VariableType type;

    private boolean changeable;

    private Node value;

    private int from = 0;
    private int to = 0;

    public enum VariableType{
        STRING, CHARACTER, INTEGER, REAL, BOOLEAN, ARRAY
    }


    /**
     * special constructor for variable nodes, used for constants in function parameters
     * @param n variable name
     * @param ty variable type
     * @param ch changeable type
     */
    public VariableNode(String n, VariableType ty, boolean ch){
        this.variableName = n;
        this.type = ty;
        this.changeable = ch;
    }

    /**
     * variable node constructor for constant variable
     * @param n name of the constant
     * @param ty type of the constant
     * @param v value for the constant
     */
    public VariableNode(String n, VariableType ty, Node v){
        this.variableName = n;
        this.type = ty;
        this.value = v;
        this.changeable = false;
    }

    /**
     * variable node construtor for an array
      * @param n name of the array
     * @param ty type of the array
     * @param f starting index of the array
     * @param t ending index of the array
     */
    public VariableNode(String n, VariableType ty, int f, int t, boolean ch) {
        this.variableName = n;
        this.type = ty;
        this.from = f;
        this.to = t;
        this.changeable = ch;
    }

    /**
     * to string method for a variable node
     * @return a string that lists the properties of the variable
     */
    public String toString() {
        if(this.to != 0){
            return this.variableName + " = array of " + this.type + " changeable = " + this.changeable + " from " + this.from + " to " + this.to;
        }
        else{
            return this.variableName + " = type: " + this.type + " value = " + this.value + " changeable = " + this.changeable;
        }
    }
}
