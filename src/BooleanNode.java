/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

public class BooleanNode extends Node {
    /**
     * boolean member of the boolean node
     */
    private boolean bool;

    /**
     * empty constructor for boolean node
     */
    public BooleanNode(){}

    /**
     * constructer for boolean node
     * @param b boolean value of the node
     */
    public BooleanNode(boolean b){
        this.bool = b;
    }

    public boolean getBool(){
        return bool;
    }


    /**
     * toString method for a Boolean Node
     * @return a string containing the boolean node and its value
     */
    public String toString() {
        return "BooleanNode(" + this.bool + ")";
    }
}
