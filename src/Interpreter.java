/**
 * ICSI 311
 * Assignment 8
 * Ryan McSweeney
 * RM483514
 * 4/9/23
 */

import java.util.HashMap;
import java.util.LinkedList;

public class Interpreter {

    private HashMap<String, InterpreterDataType> localVars;

    /**
     * interpretFunction takes in a function node and adds all variables and constants declared in the function into the local vars hash map
     * @param func function node being passed in
     */
    public void interpretFunction(FunctionNode func){
        localVars = new HashMap<>();
        for(int i = 0; i < func.getVariables().size(); i++){
            VariableNode currentVar = func.getVariables().get(i);

            if(currentVar.isArray()){
                switch (currentVar.getType()){
                    case INTEGER:
                        IntegerDataType[] intarr = new IntegerDataType[currentVar.getTo() + 1];
                        for(int j = currentVar.getFrom(); j < intarr.length; j++){
                            intarr[j] = new IntegerDataType(0);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                intarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                        break;
                    case REAL:
                        RealDataType[] realarr = new RealDataType[currentVar.getTo() + 1];
                        for(int j = currentVar.getFrom(); j < realarr.length; j++){
                            realarr[j] = new RealDataType(0);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                realarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                        break;
                    case STRING:
                        StringDataType[] stringarr = new StringDataType[currentVar.getTo() + 1];
                        for(int j = currentVar.getFrom(); j < stringarr.length; j++){
                            stringarr[j] = new StringDataType(null);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                               stringarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                        break;
                    case CHARACTER:
                        CharacterDataType[] chararr = new CharacterDataType[currentVar.getTo() + 1];
                        for(int j = currentVar.getFrom(); j < chararr.length; j++){
                            chararr[j] = new CharacterDataType('\0');
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                chararr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                        break;
                    case BOOLEAN:
                        BooleanDataType[] boolarr = new BooleanDataType[currentVar.getTo() + 1];
                        for(int j = currentVar.getFrom(); j < boolarr.length; j++){
                            boolarr[j] = new BooleanDataType(false);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                boolarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                        break;
                }
            }
            else {
                switch (currentVar.getType()) {
                    case INTEGER:
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new IntegerDataType(0));
                        } else {
                            IntegerNode currentIntNode = (IntegerNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new IntegerDataType(currentIntNode.getInteger(), false));
                        }
                        break;
                    case REAL:
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new RealDataType(0));
                        } else {
                            RealNode currentRealNode = (RealNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new RealDataType(currentRealNode.getReal(), false));
                        }
                        break;
                    case STRING:
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new StringDataType(null));
                        } else {
                            StringNode currentStringNode = (StringNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new StringDataType(currentStringNode.getString(), false));
                        }
                        break;
                    case CHARACTER:
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new CharacterDataType('\0'));
                        } else {
                            CharacterNode currentCharNode = (CharacterNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new CharacterDataType(currentCharNode.getCharacter(), false));
                        }
                        break;
                    case BOOLEAN:
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new BooleanDataType(false));
                        } else {
                            BooleanNode currentBoolNode = (BooleanNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new BooleanDataType(currentBoolNode.getBool(), false));
                        }
                        break;
                }
            }
        }
        this.InterpretBlock(func.statements);
    }

    /**
     * method interprets an incoming block of statements and passes incoming statement to the provided methods
     * @param statements list of this function or loops statements
     */
    private void InterpretBlock(LinkedList<StatementNode> statements){
        for(int i = 0; i < statements.size(); i++){
            StatementNode currentStatement = statements.get(i);

            if(currentStatement instanceof WhileNode){
                InterpretWhile((WhileNode) currentStatement);
            }
            else if(currentStatement instanceof IfNode){
                InterpretIf((IfNode) currentStatement);
            }
            else if(currentStatement instanceof RepeatNode){
                InterpretRepeat((RepeatNode) currentStatement);
            }
            else if(currentStatement instanceof ForNode){
                InterpretFor((ForNode) currentStatement);
            }
            else if(currentStatement instanceof AssignmentNode){
                InterpretAssignment((AssignmentNode) currentStatement);
            }
//            NOT IN THIS ASSIGNMENT
//            else if(currentStatement instanceof FunctionCallNode){
//
//            }
        }
    }

    /**
     * method interprets while loops, checks conditon and loops over statements until condition is false
     * @param node while node being passed in
     */
    private void InterpretWhile(WhileNode node){
        boolean condition = booleanCompare(node.getCondition());
        while(condition){
            InterpretBlock(node.getStatements());
            condition = booleanCompare(node.getCondition());
        }
    }

    /**
     * interprets if statements, checks condition, if true, executes the statements, if false, moves forward in the linked list
     * @param node if node being passed in
     */
    private void InterpretIf(IfNode node){
        boolean condition;
        if(node.getCondition() != null){
            condition = booleanCompare(node.getCondition());
            if(condition){
                InterpretBlock(node.getStatements());
            }
            else{
                if(node.getElseNode() != null){
                    InterpretIf(node.getElseNode());
                }
            }
        }
        else{
            InterpretBlock(node.getStatements());
        }
    }

    /**
     * interprets repeat loops, checks condition and loops over statements until condition is true
     * @param node repeat node being passed in
     */
    private void InterpretRepeat(RepeatNode node){
        boolean condition = booleanCompare(node.getCondition());
        while(!condition){
            InterpretBlock(node.getStatements());
            condition = booleanCompare(node.getCondition());
        }
    }

    /**
     * interprets for nodes. checks to see if the forvar has beein declared and then sets it equal to from val
     * and loops over statements until forvar reaches the to index
     * @param node for node being passed in
     */
    private void InterpretFor(ForNode node){
        if(localVars.containsKey(node.getForVar().getName())){
            IntegerDataType forVar, from, to;;
            try {
                forVar = (IntegerDataType) InterpretVariableReferenceNode(node.getForVar());
            }
            catch(Exception e){
                throw new InterpreterRuntimeException("control variable in for loop must be of type integer");
            }
            try {
                from = (IntegerDataType) expression(node.getFrom());
                to = (IntegerDataType) expression(node.getTo());
            }
            catch (Exception e){
                throw new InterpreterRuntimeException("from and to elements in for loop must be of type integer");
            }
            if(!forVar.isChangeable()){
                throw new InterpreterRuntimeException("constant immutable variable cannot be used as control variable in for loop");
            }
            if((int) from.getData() < (int) to.getData()){
                forVar.setData(((IntegerNode) (node.getFrom())).getInteger());
                while((int)forVar.getData() <= (int) to.getData()){
                    InterpretBlock(node.getStatementsList());
                    forVar.setData((int) forVar.getData() + 1);
                }
            }
        }
        else{
            throw new InterpreterRuntimeException("control variable in for loop must be declared prior to use in for loop");
        }

    }

    /**
     * interprets an assignment node. checks to make sure the target has been declared, then determines its type and sets the data accordingly
     * @param node assignment node being passed in
     */
    private void InterpretAssignment(AssignmentNode node){
        if(localVars.containsKey(node.getTarget().getName())){
            InterpreterDataType target = InterpretVariableReferenceNode(node.getTarget());
            try {
                if (target instanceof IntegerDataType) {
                    if(!((IntegerDataType) target).isChangeable()){
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    ((IntegerDataType) target).setData((int) expression(node.getValue()).getData());
                } else if (target instanceof RealDataType) {
                    if(!((RealDataType) target).isChangeable()){
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    ((RealDataType) target).setData((float) expression(node.getValue()).getData());
                } else if (target instanceof StringDataType) {
                    if(!((StringDataType) target).isChangeable()){
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    target.fromString((String) expression(node.getValue()).getData());
                } else if (target instanceof CharacterDataType) {
                    if(!((CharacterDataType) target).isChangeable()){
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    ((CharacterDataType) target).setData((char) expression(node.getValue()).getData());
                } else if (target instanceof BooleanDataType) {
                    if(!((BooleanDataType) target).isChangeable()){
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    ((BooleanDataType) target).setData((boolean) expression(node.getValue()).getData());
                }
            }
            catch(ClassCastException e){
                throw new InterpreterRuntimeException("attempting to assign a value of a different data type of declared variable \n" + e);
            }
        }
        else{
            throw new InterpreterRuntimeException("attempting to assign a variable that was not declared in the function");
        }
    }

//    NOT IN THIS ASSIGNMENT
//    private void InterpretFunctionCall(FunctionCallNode node){
//
//    }

    /**
     * interprets a math op node
     * @param node the mathopnode being passed in
     * @return a data type containing the simplified math expression
     */
    private InterpreterDataType mathOpNode(MathOpNode node){
        InterpreterDataType left, right;

        left = expression(node.getLeftNode());
        right = expression(node.getRightNode());
        if(left.getClass() == right.getClass()){
            switch (node.getOperator()) {
                case PLUS :
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() + (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() + (float) right.getData());
                    } else if (left instanceof StringDataType) {
                        return new StringDataType(left.getData() + (String) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in addition expression");
                case MINUS :
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() - (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() - (float) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in subtraction expression");
                case MULTIPLY :
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() * (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() * (float) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in multiplication expression");
                case DIVIDE :
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() / (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() / (float) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in division expression");
                case MOD :
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() % (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() % (float) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in mod expression");
            }
        }
        throw new InterpreterRuntimeException("cannot perform a math operation on 2 diffrent data types");
    }

    /**
     * interprets a variable refrence node
     * @param node vrn being passed in
     * @return the IDT at the variables location in the local vars hash map
     */
    private InterpreterDataType InterpretVariableReferenceNode(VariableReferenceNode node){
        if(localVars.containsKey(node.getName())){
            if(node.getArrayIndexExpression() == null) {
                return localVars.get(node.getName());
            }
            else{
                try {
                    IntegerDataType index = (IntegerDataType) expression(node.getArrayIndexExpression());

                    if((int) index.getData() < ((ArrayDataType)(localVars.get(node.getName()))).getStartingIndex() || (int) index.getData() > ((ArrayDataType)(localVars.get(node.getName()))).getEndingIndex()){
                        throw new InterpreterRuntimeException("attempting to reference an array at an index that is out of bounds");
                    }
                    InterpreterDataType arrayAtIndex = ((ArrayDataType)(localVars.get(node.getName()))).getArray()[(int) index.getData()];
                    return arrayAtIndex;

                }
                catch(ClassCastException e){
                    throw new InterpreterRuntimeException("invalid data found in " + node.getName() +
                            "'s array index expression. array index expression must be of type integer");
                }
            }
        }
        else{
            throw new InterpreterRuntimeException("attempting to reference a variable: "
                    + node.getName() + " that was not declared in the function");
        }
    }

    /**
     * takes in a node and returns the IDT value for the node
     * @param node node being passed in
     * @return an IDT representing the value of the node passed in
     */
    private InterpreterDataType expression(Node node){
        //math operation
        if(node instanceof MathOpNode){
            return mathOpNode((MathOpNode) node);
        }
        //variable reference
        else if(node instanceof VariableReferenceNode){
            return InterpretVariableReferenceNode((VariableReferenceNode) node);
        }
        //number or string or boolean or char
        else{
            if(node instanceof IntegerNode){
                return new IntegerDataType(((IntegerNode) node).getInteger());
            }
            else if(node instanceof RealNode){
                return new RealDataType(((RealNode) node).getReal());
            }
            else if(node instanceof StringNode){
                return new StringDataType(((StringNode) node).getString());
            }
            else if(node instanceof CharacterNode){
                return new CharacterDataType(((CharacterNode) node).getCharacter());
            }
            else if(node instanceof BooleanNode){
                return new BooleanDataType(((BooleanNode) node).getBool());
            }
            else{
                throw new InterpreterRuntimeException("invalid data found in expression");
            }
        }

    }

    /**
     * compares a boolean statement, splits it into left and right and then depending on the operation type, compares the 2 expressions
     * @param node boolean compare node being passed in
     * @return true if the condition is true, false otherwise
     */
    private boolean booleanCompare(BooleanCompareNode node){
        InterpreterDataType left, right;

        left = expression(node.getLeft());
        right = expression(node.getRight());

        if(left.getClass() == right.getClass()){
            switch(node.getComparison()){
                case EQUALS :
                    if(left instanceof IntegerDataType){
                        return (int) left.getData() == (int) right.getData();
                    }
                    else if(left instanceof RealDataType){
                        return (float) left.getData() == (float) right.getData();
                    }
                    else{
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case NOTEQUALS:
                    if(left instanceof IntegerDataType){
                        return (int) left.getData() != (int) right.getData();
                    }
                    else if(left instanceof RealDataType){
                        return (float) left.getData() != (float) right.getData();
                    }
                    else{
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case GREATERTHAN:
                    if(left instanceof IntegerDataType){
                        return (int) left.getData() > (int) right.getData();
                    }
                    else if(left instanceof RealDataType){
                        return (float) left.getData() > (float) right.getData();
                    }
                    else{
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case LESSTHAN:
                    if(left instanceof IntegerDataType){
                        return (int) left.getData() < (int) right.getData();
                    }
                    else if(left instanceof RealDataType){
                        return (float) left.getData() < (float) right.getData();
                    }
                    else{
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case GREATERTHANEQUALTO:
                    if(left instanceof IntegerDataType){
                        return (int) left.getData() >= (int) right.getData();
                    }
                    else if(left instanceof RealDataType){
                        return (float) left.getData() >= (float) right.getData();
                    }
                    else{
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case LESSTHANEQUALTO:
                    if(left instanceof IntegerDataType){
                        return (int) left.getData() <= (int) right.getData();
                    }
                    else if(left instanceof RealDataType){
                        return (float) left.getData() <= (float) right.getData();
                    }
                    else{
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
            }
        }
        throw new InterpreterRuntimeException("attempting to compare data of 2 different types");
    }



}
