/**
 * ICSI 311
 * Assignment 6
 * Ryan McSweeney
 * RM483514
 * 3/26/23
 */
package Parser;
import java.util.LinkedList;

public class WhileNode extends StatementNode{
    /**
     * boolean condition of the while loop
     */
    private final BooleanCompareNode condition;
    /**
     * list of sub statements in the while loop
     */
    private final LinkedList<StatementNode> statements;

    /**
     * constructor for a WhileNode
     * @param cond the while nodes boolean condition
     * @param statementsList list of the while nodes statements
     */
    public WhileNode(BooleanCompareNode cond, LinkedList<StatementNode> statementsList){
        this.condition = cond;
        this.statements = statementsList;
    }

    public BooleanCompareNode getCondition(){
        return condition;
    }

    public LinkedList<StatementNode> getStatements(){
        return statements;
    }

    /**
     * toString method for a WhileNode
     * @return organized information about the WhileNode
     * including its condition and statements
     */
    public String toString(){
        String s = "WHILE NODE-------------------------\n";
        s += "while: " + this.condition + "\n";
        s += "STATEMENTS-------------------------\n";
        for(int i = 0; i < this.statements.size(); i++){
            s += this.statements.get(i) + "\n";
        }
        s += "------------------------------------------";
        return s;
    }
}
