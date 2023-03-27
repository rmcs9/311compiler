/**
 * ICSI 311
 * Assignment 6
 * Ryan McSweeney
 * RM483514
 * 3/26/23
 */
import java.util.LinkedList;

public class ForNode extends StatementNode{
    /**
     * Node representing the from expression
     */
    private Node from;
    /**
     * node representing the to expression
     */
    private Node to;
    /**
     * reference to the variable being used in the for loop
     */
    VariableReferenceNode forVar;
    /**
     * list of the for loops sub statements
     */
    LinkedList<StatementNode> statementsList;

    /**
     * contructor for a ForNode
     * @param forVarName String name of the variable being referenced in the for loop
     * @param fr from Node
     * @param t to Node
     * @param list list of for node sub statements
     */
    public ForNode(String forVarName, Node fr, Node t, LinkedList<StatementNode> list){
        this.forVar = new VariableReferenceNode(forVarName);
        this.from = fr;
        this.to = t;
        this.statementsList = list;
    }

    /**
     * toString method for ForNode
     * @return a String with organized information about the ForNode
     * including its variable, its from and to range and its statements
     */
    public String toString(){
        String s = "FOR NODE-------------------------\n";
        s += "for variable: " + this.forVar + "\n";
        s += "from " + this.from + " to " + this.to + "\n";
        s += "STATEMENTS-----------------------\n";
        for(int i = 0; i < this.statementsList.size(); i++){
            s += this.statementsList.get(i).toString() + "\n";
        }
        s += "------------------------------------------";

        return s;
    }
}
