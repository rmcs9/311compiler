/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

public class CharacterNode extends Node {
    /**
     * char contained inside of the char node
     */
    private char chr;

    /**
     * empty constructor for the char node
     */
    public CharacterNode(){}

    /**
     * constructor for a char node
     * @param c the character inside of the char node
     */
    public CharacterNode(char c){
        this.chr = c;
    }

    public char getCharacter(){
        return chr;
    }


    /**
     * toString method for the char node
     * @return a string containing the char node and its value
     */
    public String toString() {
        return "CharacterNode(" + this.chr + ")";
    }
}
