/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Interpreter;
import java.util.LinkedList;
import Parser.*;


public class SemanticAnalysis {

    private ProgramNode program;

    private FunctionNode currentFunction;

    /**
     * contructor for a semantic analysis object
     * @param program the program being analyzed
     */
    public SemanticAnalysis(ProgramNode program){
        this.program = program;
        allFunctions();
    }

    /**
     * loops over all functions and calls check assignment on each one
     */
    public void allFunctions(){
        for(int i =0; i < program.getKeys().size(); i++){
            if(!program.getFunction(program.getKeys().get(i)).isBuiltIn()) {
                currentFunction = program.getFunction(program.getKeys().get(i));
                checkAssignments(currentFunction.getStatements());
            }
        }
    }

    /**
     * loops through list of statements and calls specified method if it is not a assignment node.
     * assignment node calls rightSideCheck and variableReference which both return a variable type. if these types are not
     * equal, an exception is thrown
     * @param statements list of statements being passed in from either a function node or a statement node
     */
    public void checkAssignments(LinkedList<StatementNode> statements){
        for(int i =0; i < statements.size(); i++){
            if(statements.get(i) instanceof IfNode){
                foundIf((IfNode) statements.get(i));
            }
            else if(statements.get(i) instanceof WhileNode){
                foundWhile((WhileNode) statements.get(i));
            }
            else if(statements.get(i) instanceof RepeatNode){
                foundRepeat((RepeatNode) statements.get(i));
            }
            else if(statements.get(i) instanceof ForNode){
                foundFor((ForNode) statements.get(i));
            }
            else if(statements.get(i) instanceof AssignmentNode){
                AssignmentNode node = (AssignmentNode) statements.get(i);

                VariableNode.VariableType left = variableReference(node.getTarget());
                VariableNode.VariableType right = rightSideCheck(node.getValue());

                if(left != right){
                    throw new SemanticRuntimeException("Error at assignment in function " + currentFunction.getFunctionName() + ": " + node + "\n" + "attempting to assign a variable of type " +
                        left + " to an expression of type " + right);
                }
            }
        }
    }

    /**
     * used to check the right side of an assignment statement and returns the type of the side if the expression is valid
     * @param rightSide right side of expression being passed in
     * @return the type of the right side of the expression if it is valid
     */
    public VariableNode.VariableType rightSideCheck(Node rightSide){
        if(rightSide instanceof MathOpNode){
            return mathOpNode((MathOpNode) rightSide);
        }
        else if(rightSide instanceof VariableReferenceNode){
            return variableReference((VariableReferenceNode) rightSide);
        }
        else{
            if(rightSide instanceof IntegerNode){
                return VariableNode.VariableType.INTEGER;
            }
            else if(rightSide instanceof RealNode){
                return VariableNode.VariableType.REAL;
            }
            else if(rightSide instanceof StringNode){
                return VariableNode.VariableType.STRING;
            }
            else if(rightSide instanceof CharacterNode){
                return VariableNode.VariableType.CHARACTER;
            }
            else if(rightSide instanceof BooleanNode){
                return VariableNode.VariableType.BOOLEAN;
            }
        }
        throw new SemanticRuntimeException("ERROR AT FUNCTION: " + currentFunction.getFunctionName() + ": invalid data found on right side of expression " + rightSide.toString());
    }

    /**
     * finds the type of the variable being referenced by looping through the variables and parameters of the current functions
     * @param node the variable reference node being passed in
     * @return the type of the variable being referenced
     */
    public VariableNode.VariableType variableReference(VariableReferenceNode node){
        for(int i = 0; i < currentFunction.getVariables().size(); i++){
            if(node.getName().equals(currentFunction.getVariables().get(i).getVariableName())){
                return currentFunction.getVariables().get(i).getType();
            }
        }
        for(int i = 0; i < currentFunction.getParameters().size(); i++){
            if(node.getName().equals(currentFunction.getParameters().get(i).getVariableName())){
                return currentFunction.getParameters().get(i).getType();
            }
        }
        throw new SemanticRuntimeException("ERROR AT FUNCTION: " + currentFunction.getFunctionName() + ": attempting to assign a value from a variable that does not exist. variable " + node.getName() + " does not exist");
    }

    /**
     * finds the type of the mathematical expression being passed in
     * @param node math op node being passed in
     * @return the type of the mathmatical expression. throws an exception if the expression is not valid
     */
    public VariableNode.VariableType mathOpNode(MathOpNode node){
        VariableNode.VariableType left = rightSideCheck(node.getLeftNode());
        VariableNode.VariableType right = rightSideCheck(node.getRightNode());

        if(left == right){
            return left;
        }
        else{
            throw new SemanticRuntimeException("ERROR AT FUNCTION: " + currentFunction.getFunctionName() + ": invalid expression in " + node.toString());
        }
    }

    //THESE FUNCTIONS ARE USED IN CHECK ASSIGNMENT. THEY EACH CALL CHECK ASSIGNMENT AGAIN, ON THE STATEMENT NODES LIST OF
    //SUB STATEMENTS

    public void foundIf(IfNode node){
        checkAssignments(node.getStatements());
    }

    public void foundWhile(WhileNode node){
        checkAssignments(node.getStatements());
    }

    public void foundRepeat(RepeatNode node){
        checkAssignments(node.getStatements());
    }

    public void foundFor(ForNode node){
        checkAssignments(node.getStatementsList());
    }
}