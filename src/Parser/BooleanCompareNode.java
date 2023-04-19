package Parser;

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

    public static compareType findCompareType(String comp){
        switch(comp){
            case "EQUALS":
                return compareType.EQUALS;
            case "NOTEQUALS":
                return compareType.NOTEQUALS;
            case "GREATERTHAN":
                return compareType.GREATERTHAN;
            case "LESSTHAN":
                return compareType.LESSTHAN;
            case "GREATERTHANEQUALTO":
                return compareType.GREATERTHANEQUALTO;
            case "LESSTHANEQUALTO":
                return compareType.LESSTHANEQUALTO;
        }
        throw new SyntaxErrorException("invalid comnpare type found in boolean comparison");
    }

    public String toString(){
        return "BooleanCompareNode({" + this.leftNode + "}, " + this.comparison + ", {" + this.rightNode + "})";
    }
}
