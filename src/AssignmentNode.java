public class AssignmentNode extends StatementNode{

    private VariableReferenceNode target;

    private Node value;

    public AssignmentNode(VariableReferenceNode tar, Node val){
        this.target = tar;
        this.value = val;
    }



    public String toString() {
        return "ASSIGNMENT(" + this.target + " = " + this.value + ")";
    }
}
