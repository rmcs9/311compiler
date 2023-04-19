package Lexer;

/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
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
        WORD, NUMBER, PERIOD, START, OPERATOR, STRINGLITERAL, CHARLITERAL, COMMENT, LPAREN, RPAREN, LBRACKET, RBRACKET
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

    public void setOperator(){
        this.current = stateType.OPERATOR;
    }

    public void setStringLiteral(){
        this.current = stateType.STRINGLITERAL;
    }

    public void setCharLiteral(){
        this.current = stateType.CHARLITERAL;
    }

    public void setComment(){
        this.current = stateType.COMMENT;
    }

    public void setLBRACKET(){
        this.current = stateType.LBRACKET;
    }

    public void setRBRACKET(){
        this.current = stateType.RBRACKET;
    }

    public void setLParan(){
        this.current = stateType.LPAREN;
    }

    public void setRPAREN(){
        this.current = stateType.RPAREN;
    }
    /**
     * getter method for the current state
     * @return a String containing the current state
     */
    public String getState(){
        return this.current.toString();

    }
}
