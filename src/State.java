/**
 * ICSI 311
 * Assignment 1
 * Ryan McSweeney
 * RM483514
 * 2/3/23
 */

public class State {
    /**
     *enum variable that represents the state the machine is currently in
     */
    private stateType current;

    /**
     * enum for the type of state
     */
    public enum stateType{
        WORD, NUMBER, PERIOD, START
    }

    /**
     * Constructor for a State machine object, automatically sets the current state to start state
     */
    public State(){
        this.setStart();
    }

    /**
     * setter method for the start state
     */
    public void setStart(){
        this.current = stateType.START;
    }

    /**
     * setter method for the word state
     */
    public void setWord(){
        this.current = stateType.WORD;
    }

    /**
     * setter method for the number state
     */
    public void setNumber(){
        this.current = stateType.NUMBER;
    }

    /**
     * setter method for the period state
     */
    public void setPeriod(){
        this.current = stateType.PERIOD;
    }

    /**
     * getter method for the current state
     * @return a String containing the current state
     */
    public String getState(){
        if(this.current == stateType.START){
            return "START";
        }
        else if(this.current == stateType.WORD){
            return "WORD";
        }
        else if(this.current == stateType.NUMBER){
            return "NUMBER";
        }
        else{
            return "PERIOD";
        }

    }
}
