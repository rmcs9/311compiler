public class VariableReferenceNode extends Node{

    private String name;

    private Node arrayIndexExpression;

    public VariableReferenceNode(String name, Node arrInd){
        this.name = name;
        this.arrayIndexExpression = arrInd;
    }

    public VariableReferenceNode(String name){
        this.name = name;
    }



    public String toString() {
        if(this.arrayIndexExpression != null) {
            return "VariableReferenceNode(" + this.name + " at index " + this.arrayIndexExpression + ")";
        }
        else{
            return "VariableReferenceNode(" + this.name + ")";
        }
    }
}
