public class BooleanCompareNode extends Node{

    private Node rightNode;

    private Node leftNode;

    private compareType comparison;

    public enum compareType{
        EQUALS, NOTEQUALS, GREATERTHAN, LESSTHAN, GREATERTHANEQUALTO, LESSTHANEQUALTO
    }

    public BooleanCompareNode(compareType type, Node left, Node right){
        this.comparison = type;
        this.leftNode = left;
        this.rightNode = right;
    }

    public String toString(){
        return "BooleanCompareNode({" + this.leftNode + "}, " + this.comparison + ", {" + this.rightNode + "})";
    }
}
