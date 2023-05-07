/**
 * ICSI 311
 * Assignment 9
 * Ryan McSweeney
 * RM483514
 * 4/17/23
 */
package Interpreter;

import java.util.HashMap;
import java.util.LinkedList;

import Parser.*;


public class Interpreter {

    private HashMap<String, InterpreterDataType> localVars;

    private ProgramNode currentProgram;

    public Interpreter(ProgramNode program) {
        currentProgram = program;
        localVars = new HashMap<>();
    }

    /**
     * interpretFunction takes in a function node and adds all variables and constants declared in the function into the local vars hash map
     *
     * @param func function node being passed in
     */
    public void interpretFunction(FunctionNode func, LinkedList<InterpreterDataType> params) {
        for (int i = 0; i < func.getVariables().size(); i++) {
            VariableNode currentVar = func.getVariables().get(i);


            switch (currentVar.getType()) {
                case INTEGER:
                    if (currentVar.isArray()) {
                        IntegerDataType[] intarr = new IntegerDataType[currentVar.getTo() + 1];
                        for (int j = currentVar.getFrom(); j < intarr.length; j++) {
                            intarr[j] = new IntegerDataType(0);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                intarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                    } else {
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new IntegerDataType(0));
                        } else {
                            IntegerNode currentIntNode = (IntegerNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new IntegerDataType(currentIntNode.getInteger(), false));
                        }
                    }
                    break;
                case REAL:
                    if (currentVar.isArray()) {
                        RealDataType[] realarr = new RealDataType[currentVar.getTo() + 1];
                        for (int j = currentVar.getFrom(); j < realarr.length; j++) {
                            realarr[j] = new RealDataType(0);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                realarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                    } else {
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new RealDataType(0));
                        } else {
                            RealNode currentRealNode = (RealNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new RealDataType(currentRealNode.getReal(), false));
                        }
                    }
                    break;
                case STRING:
                    if (currentVar.isArray()) {
                        StringDataType[] stringarr = new StringDataType[currentVar.getTo() + 1];
                        for (int j = currentVar.getFrom(); j < stringarr.length; j++) {
                            stringarr[j] = new StringDataType(null);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                stringarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                    } else {
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new StringDataType(null));
                        } else {
                            StringNode currentStringNode = (StringNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new StringDataType(currentStringNode.getString(), false));
                        }
                    }
                    break;
                case CHARACTER:
                    if (currentVar.isArray()) {
                        CharacterDataType[] chararr = new CharacterDataType[currentVar.getTo() + 1];
                        for (int j = currentVar.getFrom(); j < chararr.length; j++) {
                            chararr[j] = new CharacterDataType('\0');
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                chararr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                    } else {
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new CharacterDataType('\0'));
                        } else {
                            CharacterNode currentCharNode = (CharacterNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new CharacterDataType(currentCharNode.getCharacter(), false));
                        }
                    }
                    break;
                case BOOLEAN:
                    if (currentVar.isArray()) {
                        BooleanDataType[] boolarr = new BooleanDataType[currentVar.getTo() + 1];
                        for (int j = currentVar.getFrom(); j < boolarr.length; j++) {
                            boolarr[j] = new BooleanDataType(false);
                        }
                        localVars.put(currentVar.getVariableName(), new ArrayDataType(
                                boolarr,
                                currentVar.getFrom(),
                                currentVar.getTo()));
                    } else {
                        if (currentVar.isChangeable()) {
                            localVars.put(currentVar.getVariableName(), new BooleanDataType(false));
                        } else {
                            BooleanNode currentBoolNode = (BooleanNode) currentVar.getValue();
                            localVars.put(currentVar.getVariableName(), new BooleanDataType(currentBoolNode.getBool(), false));
                        }
                    }
                    break;
            }
        }

        for (int i = 0; i < func.getParameters().size(); i++) {
            if (!func.getParameters().get(i).isArray()) {
                switch (func.getParameters().get(i).getType()) {
                    case INTEGER:
                        if (params.get(i) instanceof IntegerDataType) {
                            localVars.put(func.getParameters().get(i).getVariableName(), params.get(i));
                        } else {
                            throw new InterpreterRuntimeException("incorrect type value passed in to param " +
                                    (i + 1) + " in function " + func.getFunctionName() + ". correct type: " +
                                    func.getParameters().get(i).getType());
                        }
                        break;
                    case REAL:
                        if (params.get(i) instanceof RealDataType) {
                            localVars.put(func.getParameters().get(i).getVariableName(), params.get(i));
                        } else {
                            throw new InterpreterRuntimeException("incorrect type value passed in to param " +
                                    (i + 1) + " in function " + func.getFunctionName() + ". correct type: " +
                                    func.getParameters().get(i).getType());
                        }
                        break;
                    case STRING:
                        if (params.get(i) instanceof StringDataType) {
                            localVars.put(func.getParameters().get(i).getVariableName(), params.get(i));
                        } else {
                            throw new InterpreterRuntimeException("incorrect type value passed in to param " +
                                    (i + 1) + " in function " + func.getFunctionName() + ". correct type: " +
                                    func.getParameters().get(i).getType());
                        }
                        break;
                    case CHARACTER:
                        if (params.get(i) instanceof CharacterDataType) {
                            localVars.put(func.getParameters().get(i).getVariableName(), params.get(i));
                        } else {
                            throw new InterpreterRuntimeException("incorrect type value passed in to param " +
                                    (i + 1) + " in function " + func.getFunctionName() + ". correct type: " +
                                    func.getParameters().get(i).getType());
                        }
                        break;
                    case BOOLEAN:
                        if (params.get(i) instanceof BooleanDataType) {
                            localVars.put(func.getParameters().get(i).getVariableName(), params.get(i));
                        } else {
                            throw new InterpreterRuntimeException("incorrect type value passed in to param " +
                                    (i + 1) + " in function " + func.getFunctionName() + ". correct type: " +
                                    func.getParameters().get(i).getType());
                        }
                        break;
                }
            } else {
                if (params.get(i) instanceof ArrayDataType) {
                    localVars.put(func.getParameters().get(i).getVariableName(), params.get(i));
                } else {
                    throw new InterpreterRuntimeException("incorrect type value passed in to param " +
                            (i + 1) + " in function " + func.getFunctionName() + ". correct type: ARRAY");
                }
            }

        }
        this.InterpretBlock(func.getStatements());
    }

    /**
     * method interprets an incoming block of statements and passes incoming statement to the provided methods
     *
     * @param statements list of this function or loops statements
     */
    private void InterpretBlock(LinkedList<StatementNode> statements) {
        for (int i = 0; i < statements.size(); i++) {
            StatementNode currentStatement = statements.get(i);

            if (currentStatement instanceof WhileNode) {
                InterpretWhile((WhileNode) currentStatement);
            } else if (currentStatement instanceof IfNode) {
                InterpretIf((IfNode) currentStatement);
            } else if (currentStatement instanceof RepeatNode) {
                InterpretRepeat((RepeatNode) currentStatement);
            } else if (currentStatement instanceof ForNode) {
                InterpretFor((ForNode) currentStatement);
            } else if (currentStatement instanceof AssignmentNode) {
                InterpretAssignment((AssignmentNode) currentStatement);
            } else if (currentStatement instanceof FunctionCallNode) {
                InterpretFunctionCall((FunctionCallNode) currentStatement);
            }
        }
    }

    /**
     * method interprets while loops, checks conditon and loops over statements until condition is false
     *
     * @param node while node being passed in
     */
    private void InterpretWhile(WhileNode node) {
        boolean condition = booleanCompare(node.getCondition());
        while (condition) {
            InterpretBlock(node.getStatements());
            condition = booleanCompare(node.getCondition());
        }
    }

    /**
     * interprets if statements, checks condition, if true, executes the statements, if false, moves forward in the linked list
     *
     * @param node if node being passed in
     */
    private void InterpretIf(IfNode node) {
        boolean condition;
        if (node.getCondition() != null) {
            condition = booleanCompare(node.getCondition());
            if (condition) {
                InterpretBlock(node.getStatements());
            } else {
                if (node.getElseNode() != null) {
                    InterpretIf(node.getElseNode());
                }
            }
        } else {
            InterpretBlock(node.getStatements());
        }
    }

    /**
     * interprets repeat loops, checks condition and loops over statements until condition is true
     *
     * @param node repeat node being passed in
     */
    private void InterpretRepeat(RepeatNode node) {
        boolean condition = booleanCompare(node.getCondition());
        while (!condition) {
            InterpretBlock(node.getStatements());
            condition = booleanCompare(node.getCondition());
        }
    }

    /**
     * interprets for nodes. checks to see if the forvar has beein declared and then sets it equal to from val
     * and loops over statements until forvar reaches the to index
     *
     * @param node for node being passed in
     */
    private void InterpretFor(ForNode node) {
        if (localVars.containsKey(node.getForVar().getName())) {
            IntegerDataType forVar, from, to;
            try {
                forVar = (IntegerDataType) InterpretVariableReferenceNode(node.getForVar());
            } catch (Exception e) {
                throw new InterpreterRuntimeException("control variable in for loop must be of type integer");
            }
            try {
                from = (IntegerDataType) expression(node.getFrom());
                to = (IntegerDataType) expression(node.getTo());
            } catch (Exception e) {
                throw new InterpreterRuntimeException("from and to elements in for loop must be of type integer");
            }
            if (!forVar.isChangeable()) {
                throw new InterpreterRuntimeException("constant immutable variable cannot be used as control variable in for loop");
            }
            if ((int) from.getData() < (int) to.getData()) {
                forVar.setData(((IntegerNode) (node.getFrom())).getInteger());
                while ((int) forVar.getData() <= (int) to.getData()) {
                    InterpretBlock(node.getStatementsList());
                    forVar.setData((int) forVar.getData() + 1);
                }
            }
        } else {
            throw new InterpreterRuntimeException("control variable in for loop must be declared prior to use in for loop");
        }

    }

    /**
     * interprets an assignment node. checks to make sure the target has been declared, then determines its type and sets the data accordingly
     *
     * @param node assignment node being passed in
     */
    private void InterpretAssignment(AssignmentNode node) {
        if (localVars.containsKey(node.getTarget().getName())) {
            InterpreterDataType target = InterpretVariableReferenceNode(node.getTarget());
            try {
                if (target instanceof IntegerDataType) {
                    if (!target.isChangeable()) {
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    target.setData(expression(node.getValue()).getData());
                } else if (target instanceof RealDataType) {
                    if (!target.isChangeable()) {
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    target.setData(expression(node.getValue()).getData());
                } else if (target instanceof StringDataType) {
                    if (!target.isChangeable()) {
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    target.fromString((String) expression(node.getValue()).getData());
                } else if (target instanceof CharacterDataType) {
                    if (!target.isChangeable()) {
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    target.setData(expression(node.getValue()).getData());
                } else if (target instanceof BooleanDataType) {
                    if (!target.isChangeable()) {
                        throw new InterpreterRuntimeException("constant immutable variable cannot be assigned a value");
                    }
                    target.setData(expression(node.getValue()).getData());
                }
            } catch (ClassCastException e) {
                throw new InterpreterRuntimeException("attempting to assign a value of a different data type of declared variable \n" + e);
            }
        } else {
            throw new InterpreterRuntimeException("attempting to assign a variable that was not declared in the function");
        }
    }

    /**
     * interprets a function call node. makes a copy of the parameters from the function call node, then passes those in to execute or interpret function.
     * finally, the method checks to see if all invocations are marked as var before changing any values
     *
     * @param node function call node being passed in
     */
    private void InterpretFunctionCall(FunctionCallNode node) {
        if (currentProgram.getFunction(node.getFunctionName()) != null) {

            FunctionNode functionCalled = currentProgram.getFunction(node.getFunctionName());
            LinkedList<ParameterNode> functionCallParamsList = node.getParametersList();

            LinkedList<InterpreterDataType> params = new LinkedList<>();
            for (ParameterNode parameterNode : functionCallParamsList) {
                InterpreterDataType currentVar;
                if (parameterNode.getVarIdentifier() == null) {
                    currentVar = expression(parameterNode.getParamExpression());
                } else {
                    currentVar = expression(parameterNode.getVarIdentifier());
                }

                InterpreterDataType copyVar;
                if (currentVar instanceof IntegerDataType) {
                    copyVar = new IntegerDataType((int) currentVar.getData());
                } else if (currentVar instanceof RealDataType) {
                    copyVar = new RealDataType((float) currentVar.getData());
                } else if (currentVar instanceof StringDataType) {
                    copyVar = new StringDataType(currentVar.toString());
                } else if (currentVar instanceof CharacterDataType) {
                    copyVar = new CharacterDataType((char) currentVar.getData());
                } else if (currentVar instanceof BooleanDataType) {
                    copyVar = new BooleanDataType((boolean) currentVar.getData());
                } else if (currentVar instanceof ArrayDataType) {
                    copyVar = new ArrayDataType(((ArrayDataType) currentVar).getArray());
                } else {
                    throw new InterpreterRuntimeException("invalid data");
                }
                params.add(copyVar);
            }

            //function is user created
            if (!functionCalled.isBuiltIn()) {
                //checks if correct amount of parameters are passed in
                if (functionCalled.getParameters().size() != node.getParametersList().size()) {
                    throw new InterpreterRuntimeException("incorrect parameters passed in to function " + node.getFunctionName()
                            + " user passed in " + node.getParametersList().size() + " required number of params: " + functionCalled.getParameters().size());
                }
                interpretFunction(functionCalled, params);
            }
            //function is built in
            else {
                ((BuiltIn) functionCalled).execute(params);
            }

            for (int i = 0; i < params.size(); i++) {
                if (functionCalled.isBuiltIn()) {
                    if ((functionCalled.isVariadic() && !functionCalled.getFunctionName().equals("Write")) || (((BuiltIn) functionCalled).isVar(i) && node.getParametersList().get(i).isVar())) {
                        if (localVars.get(node.getParametersList().get(i).getVarIdentifier().getName()).isChangeable()) {
                            localVars.get(node.getParametersList().get(i).getVarIdentifier().getName()).setData(params.get(i).getData());
                        } else {
                            throw new InterpreterRuntimeException("attempting to mark a constant variable " + node.getParametersList().get(i).getVarIdentifier().getName() + " as var in a function call");
                        }
                    }
                } else {
                    if (functionCalled.getParameters().get(i).isVar() && node.getParametersList().get(i).isVar()) {
                        if (localVars.get(node.getParametersList().get(i).getVarIdentifier().getName()).isChangeable()) {
                            localVars.get(node.getParametersList().get(i).getVarIdentifier().getName()).setData(params.get(i).getData());
                        } else {
                            throw new InterpreterRuntimeException("attempting to mark a constant variable " + node.getParametersList().get(i).getVarIdentifier().getName() + " as var in a function call");
                        }
                    }
                }
            }

        } else {
            throw new InterpreterRuntimeException("attempting to call function "
                    + node.getFunctionName() + " that is not present in the program or a builtin method");
        }
    }

    /**
     * interprets a math op node
     *
     * @param node the mathopnode being passed in
     * @return a data type containing the simplified math expression
     */
    private InterpreterDataType mathOpNode(MathOpNode node) {
        InterpreterDataType left, right;

        left = expression(node.getLeftNode());
        right = expression(node.getRightNode());
        if (left.getClass() == right.getClass()) {
            switch (node.getOperator()) {
                case PLUS:
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() + (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() + (float) right.getData());
                    } else if (left instanceof StringDataType) {
                        return new StringDataType(left.getData() + (String) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in addition expression");
                case MINUS:
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() - (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() - (float) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in subtraction expression");
                case MULTIPLY:
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() * (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() * (float) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in multiplication expression");
                case DIVIDE:
                    if (left instanceof IntegerDataType) {
                        return new IntegerDataType((int) left.getData() / (int) right.getData());
                    } else if (left instanceof RealDataType) {
                        return new RealDataType((float) left.getData() / (float) right.getData());
                    } else throw new InterpreterRuntimeException("invalid data type in division expression");
                case MOD:
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
     *
     * @param node vrn being passed in
     * @return the IDT at the variables location in the local vars hash map
     */
    private InterpreterDataType InterpretVariableReferenceNode(VariableReferenceNode node) {
        if (localVars.containsKey(node.getName())) {
            if (node.getArrayIndexExpression() == null) {
                return localVars.get(node.getName());
            } else {
                try {
                    IntegerDataType index = (IntegerDataType) expression(node.getArrayIndexExpression());

                    if ((int) index.getData() < ((ArrayDataType) (localVars.get(node.getName()))).getStartingIndex() || (int) index.getData() > ((ArrayDataType) (localVars.get(node.getName()))).getEndingIndex()) {
                        throw new InterpreterRuntimeException("attempting to reference an array at an index that is out of bounds");
                    }
                    InterpreterDataType arrayAtIndex = ((ArrayDataType) (localVars.get(node.getName()))).getArray()[(int) index.getData()];
                    return arrayAtIndex;

                } catch (ClassCastException e) {
                    throw new InterpreterRuntimeException("invalid data found in " + node.getName() +
                            "'s array index expression. array index expression must be of type integer");
                }
            }
        } else {
            throw new InterpreterRuntimeException("attempting to reference a variable: "
                    + node.getName() + " that was not declared in the function");
        }
    }

    /**
     * takes in a node and returns the IDT value for the node
     *
     * @param node node being passed in
     * @return an IDT representing the value of the node passed in
     */
    private InterpreterDataType expression(Node node) {
        //math operation
        if (node instanceof MathOpNode) {
            return mathOpNode((MathOpNode) node);
        }
        //variable reference
        else if (node instanceof VariableReferenceNode) {
            return InterpretVariableReferenceNode((VariableReferenceNode) node);
        }
        //number or string or boolean or char
        else {
            if (node instanceof IntegerNode) {
                return new IntegerDataType(((IntegerNode) node).getInteger());
            } else if (node instanceof RealNode) {
                return new RealDataType(((RealNode) node).getReal());
            } else if (node instanceof StringNode) {
                return new StringDataType(((StringNode) node).getString());
            } else if (node instanceof CharacterNode) {
                return new CharacterDataType(((CharacterNode) node).getCharacter());
            } else if (node instanceof BooleanNode) {
                return new BooleanDataType(((BooleanNode) node).getBool());
            } else {
                throw new InterpreterRuntimeException("invalid data found in expression");
            }
        }

    }

    /**
     * compares a boolean statement, splits it into left and right and then depending on the operation type, compares the 2 expressions
     *
     * @param node boolean compare node being passed in
     * @return true if the condition is true, false otherwise
     */
    private boolean booleanCompare(BooleanCompareNode node) {
        InterpreterDataType left, right;

        left = expression(node.getLeft());
        right = expression(node.getRight());

        if (left.getClass() == right.getClass()) {
            switch (node.getComparison()) {
                case EQUALS:
                    if (left instanceof IntegerDataType) {
                        return (int) left.getData() == (int) right.getData();
                    } else if (left instanceof RealDataType) {
                        return (float) left.getData() == (float) right.getData();
                    } else {
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case NOTEQUALS:
                    if (left instanceof IntegerDataType) {
                        return (int) left.getData() != (int) right.getData();
                    } else if (left instanceof RealDataType) {
                        return (float) left.getData() != (float) right.getData();
                    } else {
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case GREATERTHAN:
                    if (left instanceof IntegerDataType) {
                        return (int) left.getData() > (int) right.getData();
                    } else if (left instanceof RealDataType) {
                        return (float) left.getData() > (float) right.getData();
                    } else {
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case LESSTHAN:
                    if (left instanceof IntegerDataType) {
                        return (int) left.getData() < (int) right.getData();
                    } else if (left instanceof RealDataType) {
                        return (float) left.getData() < (float) right.getData();
                    } else {
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case GREATERTHANEQUALTO:
                    if (left instanceof IntegerDataType) {
                        return (int) left.getData() >= (int) right.getData();
                    } else if (left instanceof RealDataType) {
                        return (float) left.getData() >= (float) right.getData();
                    } else {
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
                case LESSTHANEQUALTO:
                    if (left instanceof IntegerDataType) {
                        return (int) left.getData() <= (int) right.getData();
                    } else if (left instanceof RealDataType) {
                        return (float) left.getData() <= (float) right.getData();
                    } else {
                        throw new InterpreterRuntimeException("invalid types for comparison, must be number");
                    }
            }
        }
        throw new InterpreterRuntimeException("attempting to compare data of 2 different types");
    }
}
