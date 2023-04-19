/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */
package Parser;
public class MathOpNode extends Node{
    //right node of the math op node
    private final Node rightNode;
    //left node of the math op node
    private final Node leftNode;

    //operator for the math op node
    private final operationType operator;

    /**
     * constructor for a new math op node
     * @param type operator type of the node
     * @param left left node of the math op node
     * @param right right node of the math op node
     */
    public MathOpNode(operationType type, Node left, Node right){
        this.operator = type;
        this.rightNode = right;
        this.leftNode = left;
    }

    public Node getRightNode(){
        return rightNode;
    }

    public Node getLeftNode(){
        return leftNode;
    }

    public operationType getOperator(){
        return operator;
    }

    /**
     * enumerator for the possible operation types for the node
     */
    public enum operationType{
        PLUS, MINUS, MULTIPLY, DIVIDE, MOD
    }

    /**
     * to string for the math op node
     * @return a formatted string for the math op node
     */
    public String toString() {
        return "MathOpNode({" + this.leftNode + "}, " + this.operator + ", {" + this.rightNode + "})";
    }
}
