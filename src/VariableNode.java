/**
 * ICSI 311
 * Assignment 7
 * Ryan McSweeney
 * RM483514
 * 4/2/23
 */

public class VariableNode extends Node{

    private String variableName;

    private VariableType type;

    private boolean changeable;

    private Node value;

    private int from = 0;
    private int to = 0;

    private float realFrom = 0;

    private float realTo = 0;

    boolean isArray = false;

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
     * variable node construtor for an array or variable of type Integer, String with type limits
      * @param n name of the array or variable
     * @param ty type of the array or variable
     * @param f starting index of the array or lower bound of the type limit
     * @param t ending index of the array or upper bound of the type limit
     * @param isArr boolean, true if variable is an array, false if otherwise
     */
    public VariableNode(String n, VariableType ty, int f, int t, boolean ch, boolean isArr) {
        this.variableName = n;
        this.type = ty;
        this.from = f;
        this.to = t;
        this.changeable = ch;
        this.isArray = isArr;
    }

    public VariableNode(String n, VariableType ty, int f, int t, boolean ch) {
        this.variableName = n;
        this.type = ty;
        this.from = f;
        this.to = t;
        this.changeable = ch;
    }

    /**
     * variable node contructor for a real variable with type limits
     * @param n name of the variable
     * @param ty type of the variable (will always be real)
     * @param f lower bound of the type limit
     * @param t upper bound of the type limit
     * @param ch whether or not the variable is changeable or not
     */
    public VariableNode(String n, VariableType ty, float f, float t, boolean ch){
        this.variableName = n;
        this.type = ty;
        this.realFrom = f;
        this.realTo = t;
        this.changeable = ch;
    }

    public String getVariableName(){
        return this.variableName;
    }

    public int getFrom(){
        return this.from;
    }

    public int getTo(){
        return to;
    }

    /**
     * to string method for a variable node
     * @return a string that lists the properties of the variable
     */
    public String toString() {
        if(this.type != VariableType.REAL){
            if(this.isArray){
                return this.variableName + " = array of " + this.type + " changeable = " + this.changeable + " from " + this.from + " to " + this.to;
            }
            else{
                if(this.to != 0){
                    return this.variableName + " = type: " + this.type + " value = " + this.value + " changeable = " + this.changeable + " type limit from " + this.from + " to " + this.to;
                }
                else{
                    return this.variableName + " = type: " + this.type + " value = " + this.value + " changeable = " + this.changeable;
                }
            }
        }
        else{
            if(this.isArray){
                return this.variableName + " = array of " + this.type + " changeable = " + this.changeable + " from " + this.from + " to " + this.to;
            }
            else{
                if(this.realTo != 0){
                    return this.variableName + " = type: " + this.type + " value = " + this.value + " changeable = " + this.changeable + " type limit from " + this.realFrom + " to " + this.realTo;
                }
                else{
                    return this.variableName + " = type: " + this.type + " value = " + this.value + " changeable = " + this.changeable;
                }

            }
        }
    }
}
