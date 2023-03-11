/**
 * ICSI 311
 * Assignment 3
 * Ryan McSweeney
 * RM483514
 * 2/26/23
 */

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
