/**
 * ICSI 311
 * Assignment 3
 * Ryan McSweeney
 * RM483514
 * 2/26/23
 */

public class IntegerNode extends Node{
    //private member containing the integer
    private int integer;

    /**
     * cionstructer for new integer node
     * @param member integer in the node
     */
    public IntegerNode(int member){
        this.integer = member;
    }

    /**
     * getter method for the integer member of the node
     * @return an integer contained in the node
     */
    public int getInteger(){
        return this.integer;
    }

    /**
     * to string for integer node
     * @return a string format for the integer node
     */
    public String toString() {
        return "IntegerNode(" + this.integer + ")";
    }
}
