/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

public class StringNode extends Node{
    /**
     * string contained in the string node
     */
    private String str;

    /**
     * empty constructor for a string node
     */
    public StringNode(){}

    /**
     * constructor for a string node
     * @param s string being added to the string node
     */
    public StringNode(String s){
        this.str = s;
    }

    /**
     * toString method for a string node
     * @return a string containing the string node and the string contained in the string node
     */
    public String toString() {
        return "StringNode(" + this.str + ")";
    }
}
