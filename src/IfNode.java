/**
 * ICSI 311
 * Assignment 6
 * Ryan McSweeney
 * RM483514
 * 3/26/23
 */
import java.util.LinkedList;

public class IfNode extends StatementNode{

    /**
     * boolean condition for the if statement
     */
    private final BooleanCompareNode condition;
    /**
     * list of sub statements in the if statement
     */
    private final LinkedList<StatementNode> statements;
    /**
     * link to the elsif or else node, directly after the ifNode
     */
    private final IfNode elseNode;

    /**
     * ifNode constructor for a single if with no ifels or else components
     * @param cond ifNodes conditional statement
     * @param statementList statements within the if statement
     */
    public IfNode(BooleanCompareNode cond, LinkedList<StatementNode> statementList){
        this.condition = cond;
        this.statements =statementList;
        this.elseNode = null;
    }

    /**
     * IfNode constructor for a IfNode with more than 0 elsif or else components
     * @param cond ifNodes conditional statement
     * @param statementList statements of the if/ifels/else statement
     * @param elseStatement ifNode containing the next elsif/else in the chain
     */
    public IfNode(BooleanCompareNode cond, LinkedList<StatementNode> statementList, IfNode elseStatement){
        this.condition = cond;
        this.statements = statementList;
        this.elseNode = elseStatement;
    }

    /**
     * ifNode constructor for a else statement
     * @param statementList statements in the else statement
     */
    public IfNode(LinkedList<StatementNode> statementList){
        this.condition = null;
        this.statements = statementList;
        this.elseNode = null;
    }

    public BooleanCompareNode getCondition(){
        return condition;
    }

    public LinkedList<StatementNode> getStatements(){
        return statements;
    }

    /**
     * getter method for the IfNodes elseNode link
     * @return the elseNode of the IfNode this function is called on
     */
    public IfNode getElseNode(){
        return this.elseNode;
    }

    /**
     * toString method for IfNode
     * @return a String containing organized information about the IfNode, including
     * its condition, statements, and other elsif or else links the node has
     */
    public String toString(){
        String s = "IF NODE-------------------------\n";
        s += "if: " + this.condition + "\n";
        s += "STATEMENTS-----------------------\n";
        for(int i = 0; i < this.statements.size(); i++){
            s += this.statements.get(i) + "\n";
        }

        IfNode elsif = this.elseNode;
        while(elsif != null){
            if(elsif.getElseNode() != null){
                s += "ELSIF NODE-------------------------\n";
                s += "elsif: " + this.condition + "\n";
                s += "STATEMENTS-----------------------\n";
                for(int i = 0; i < elsif.statements.size(); i++){
                    s += elsif.statements.get(i) + "\n";
                }
                elsif = elsif.getElseNode();
            }
            else{
                s += "ELSE NODE-------------------------\n";
                s += "else: " + this.condition + "\n";
                s += "STATEMENTS-----------------------\n";
                for(int i = 0; i < elsif.statements.size(); i++){
                    s += elsif.statements.get(i) + "\n";
                }
                elsif = null;
            }
        }
        s += "------------------------------------------";
        return s;
    }
}
