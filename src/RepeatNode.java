/**
 * ICSI 311
 * Assignment 6
 * Ryan McSweeney
 * RM483514
 * 3/26/23
 */
import java.util.LinkedList;

public class RepeatNode extends StatementNode{
    /**
     * repeat nodes boolean condition
     */
    private final BooleanCompareNode condition;
    /**
     * list of repeat nodes sub statements
     */
    private final LinkedList<StatementNode> statements;

    /**
     * constructor for RepeatNodes
     * @param cond the RepeatNodes condition
     * @param statementsList the RepeatNodes list of statements
     */
    public RepeatNode(BooleanCompareNode cond, LinkedList<StatementNode> statementsList){
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
     * toString method for RepeatNode
     * @return a String containing organized information about the
     * RepeatNode including its condition, and its statements
     */
    public String toString(){
        String s = "REPEAT NODE-------------------------\n";
        s += "repeat while: " + this.condition + "\n";
        s += "STATEMENTS-------------------------\n";
        for(int i = 0; i < this.statements.size(); i++){
            s += this.statements.get(i) + "\n";
        }
        s += "------------------------------------------";
        return s;
    }
}
