public class BooleanCompareNode extends Node{

    private final Node rightNode;

    private final Node leftNode;

    private final compareType comparison;

    public enum compareType{
        EQUALS, NOTEQUALS, GREATERTHAN, LESSTHAN, GREATERTHANEQUALTO, LESSTHANEQUALTO
    }

    public BooleanCompareNode(compareType type, Node left, Node right){
        this.comparison = type;
        this.leftNode = left;
        this.rightNode = right;
    }

    public Node getLeft(){
        return leftNode;
    }

    public Node getRight(){
        return rightNode;
    }

    public compareType getComparison(){
        return comparison;
    }

    public String toString(){
        return "BooleanCompareNode({" + this.leftNode + "}, " + this.comparison + ", {" + this.rightNode + "})";
    }
}
