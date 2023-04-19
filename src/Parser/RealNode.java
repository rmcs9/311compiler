/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */
package Parser;
public class RealNode extends Node{
    //float contained inside of the real node
    private float real;

    /**
     * constructer for a new real node
     * @param member the float contained inside the real node
     */
    public RealNode(float member){
        this.real = member;
    }

    public RealNode(){

    }

    /**
     * getter method for the float member of the ral node
     * @return the float contained in the real node
     */
    public float getReal(){
        return this.real;
    }

    /**
     * toString method for real node
     * @return string format of the real node
     */
    public String toString(){
        return "RealNode(" + this.real + ")";
    }



}
