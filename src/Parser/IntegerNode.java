/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */
package Parser;
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

    public IntegerNode(){}

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
