public class AssignmentNode extends StatementNode{

    private final VariableReferenceNode target;

    private final Node value;

    public AssignmentNode(VariableReferenceNode tar, Node val){
        this.target = tar;
        this.value = val;
    }

    public VariableReferenceNode getTarget(){
        return target;
    }

    public Node getValue(){
        return value;
    }



    public String toString() {
        return "ASSIGNMENT(" + this.target + " = " + this.value + ")";
    }
}
