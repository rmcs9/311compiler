/**
 * ICSI 311
 * Assignment 4
 * Ryan McSweeney
 * RM483514
 * 3/5/23
 */

import java.util.LinkedList;
import java.util.Objects;

public class Parser {

    private LinkedList<Token> parserTokens = new LinkedList<Token>();

    /**
     * constructor for a parser
     *
     * @param tokens list of tokens received from the lexer
     */
    public Parser(LinkedList<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            parserTokens.add(tokens.get(i));
        }
    }

    /**
     * match an remove method used to assist the parser in parsing tokens
     *
     * @param tokenType type of token being checked for a match
     * @return the token at the top of the queue
     */
    private Token matchAndRemove(Token.tokenType tokenType) {
        if (this.parserTokens.peek().getTokenType() == tokenType) {
            return this.parserTokens.remove();
        } else {
            return null;
        }
    }

    /**
     * method used to assist parser in parsing tokens. checks if endofline is present and removes it if so
     */
    private void expectEndOfLine() {
        Token tok = matchAndRemove(Token.tokenType.ENDOFLINE);
        if (tok == null) {
            throw new SyntaxErrorException("NO END OF LINE FOUND");
        }
    }

    /**
     * checks the current token in the queue at a given index
     *
     * @param t integer representing how many tokens down the queue to check
     * @return the token at the given index 't' in the queue
     */
    private Token peek(int t) {
        if (this.parserTokens.get(t) == null) {
            return null;
        } else {
            return this.parserTokens.get(t);
        }
    }

    /**
     * parse method called at main to parse an incoming list of tokens
     *
     * @return the program node created
     */
    public Node parse() {
        FunctionNode func;
        ProgramNode program = new ProgramNode();
        while (!parserTokens.isEmpty()) {
            func = function();
            program.addFunction(func);
        }
        return program;
    }

    /**
     * expression method called by parse which calls term and loops for a plus or a minus
     *
     * @return a node containing the expression
     */
    private Node expression() {
        Node term1 = term();
        Token expressionTok = new Token();

        while (peek(0).getTokenType() == Token.tokenType.PLUS || peek(0).getTokenType() == Token.tokenType.MINUS) {
            expressionTok = matchAndRemove(Token.tokenType.PLUS);

            if (expressionTok != null && expressionTok.getTokenType() == Token.tokenType.PLUS) {
                Node term2 = term();
                term1 = new MathOpNode(MathOpNode.operationType.PLUS, term1, term2);
            } else {
                expressionTok = matchAndRemove(Token.tokenType.MINUS);
            }

            if (expressionTok != null && expressionTok.getTokenType() == Token.tokenType.MINUS) {
                Node term2 = term();
                term1 = new MathOpNode(MathOpNode.operationType.MINUS, term1, term2);
            }
        }
        return term1;
    }

    /**
     * method called by expression, calls factor and then loops for a multiply divide or mod
     *
     * @return a node containing the term
     */
    private Node term() {
        Node factor1 = factor();
        Token termTok = new Token();

        while (peek(0).getTokenType() == Token.tokenType.MULTIPLY ||
                peek(0).getTokenType() == Token.tokenType.DIVIDE ||
                peek(0).getTokenType() == Token.tokenType.MOD) {

            termTok = matchAndRemove(Token.tokenType.MULTIPLY);

            if (termTok != null && termTok.getTokenType() == Token.tokenType.MULTIPLY) {
                Node factor2 = factor();
                factor1 = new MathOpNode(MathOpNode.operationType.MULTIPLY, factor1, factor2);
            }

            termTok = matchAndRemove(Token.tokenType.DIVIDE);

            if (termTok != null && termTok.getTokenType() == Token.tokenType.DIVIDE) {
                Node factor2 = factor();
                factor1 = new MathOpNode(MathOpNode.operationType.DIVIDE, factor1, factor2);
            }

            termTok = matchAndRemove(Token.tokenType.MOD);

            if (termTok != null && termTok.getTokenType() == Token.tokenType.MOD) {
                Node factor2 = factor();
                factor1 = new MathOpNode(MathOpNode.operationType.MOD, factor1, factor2);
            }
        }
        return factor1;
    }

    /**
     * method called by term and determines if incoming input is a number, negative or LPAREN
     *
     * @return a node containing the factor
     */
    private Node factor() {
        Token factor = new Token();
        boolean isNegative = false;

        if (peek(0).getTokenType() == Token.tokenType.MINUS ||
                peek(0).getTokenType() == Token.tokenType.NUMBER ||
                peek(0).getTokenType() == Token.tokenType.LPAREN) {

            factor = matchAndRemove(Token.tokenType.MINUS);

            if (factor != null) {
                isNegative = true;
                factor = matchAndRemove(Token.tokenType.NUMBER);
            } else {
                factor = matchAndRemove(Token.tokenType.NUMBER);
            }

            if (factor != null) {
                if (isNegative) {
                    if (Float.parseFloat(factor.getTokenContents()) % 1 == 0) {
                        return new IntegerNode(Integer.parseInt((factor.getTokenContents())) -
                                (2 * (Integer.parseInt(factor.getTokenContents()))));
                    } else {
                        return new RealNode(Float.parseFloat(factor.getTokenContents()) -
                                (2 * (Float.parseFloat(factor.getTokenContents()))));
                    }
                } else {
                    if (Float.parseFloat(factor.getTokenContents()) % 1 == 0) {
                        return new IntegerNode(Integer.parseInt(factor.getTokenContents()));
                    } else {
                        return new RealNode(Float.parseFloat(factor.getTokenContents()));
                    }
                }
            } else {
                factor = matchAndRemove(Token.tokenType.LPAREN);
            }

            if (factor != null) {
                Node paranExpression = expression();
                matchAndRemove(Token.tokenType.RPAREN);
                return paranExpression;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * function method parses a function and returns a function node
     *
     * @return function node
     */
    private FunctionNode function() {
        String functionName;
        LinkedList<VariableNode> params = new LinkedList<VariableNode>();
        LinkedList<VariableNode> vars = new LinkedList<VariableNode>();
        LinkedList<Node> statements = new LinkedList<Node>();

        //expects a define keyword, if not found exception is thrown
        Token token = matchAndRemove(Token.tokenType.DEFINE);
        if (token == null) {
            throw new SyntaxErrorException("define keyword not found at line " + peek(0).getTokenLine());
        }
        token = matchAndRemove(Token.tokenType.IDENTIFIER);
        //expects a identifier after define keyword, if not found exception is thrown
        if (token == null) {
            throw new SyntaxErrorException("identifier expected after function keyword at line" + peek(0).getTokenLine());
        }
        functionName = token.getTokenContents();
        //LPAREN is expected after function name, if not found, expcetion is thrown
        token = matchAndRemove(Token.tokenType.LPAREN);
        if (token == null) {
            throw new SyntaxErrorException("'(' expected after function name at line " + peek(0).getTokenLine());
        }
        //parameter declarations is called to parse parameters
        params = parameterDeclarations();

        //RPAREN is expected at the end of the parameters, if it is not found, expcetion is thrown
        token = matchAndRemove(Token.tokenType.RPAREN);
        if (token == null) {
            throw new SyntaxErrorException("')' expected after parameters at line " + peek(0).getTokenLine());
        }
        //end of line is expected after right paren
        expectEndOfLine();

        //loop expects either constants or variables keyword for every incoming line and calls constants() and variables()
        //respectfully for parsing
        while (!parserTokens.isEmpty()) {
            if (peek(0).getTokenType() == Token.tokenType.CONSTANTS || peek(0).getTokenType() == Token.tokenType.VARIABLES) {
                //if next line has the constants keyword, constants() is called
                if (peek(0).getTokenType() == Token.tokenType.CONSTANTS) {
                    token = matchAndRemove(Token.tokenType.CONSTANTS);
                    vars.addAll(constants());
                    expectEndOfLine();
                    //if next line has the variables keyword, variables() is called
                } else if (peek(0).getTokenType() == Token.tokenType.VARIABLES) {
                    token = matchAndRemove(Token.tokenType.VARIABLES);
                    vars.addAll(variables());
                    expectEndOfLine();
                }
            }
            //if neither is found, loop is broken
            else {
                break;
            }
        }

        if (!parserTokens.isEmpty()) {
            //indent is expected after all local constants and varibles are declared
            token = matchAndRemove(Token.tokenType.INDENT);
            if (token != null) {
                //expression is called once if an indent is found
                Node root = expression();
                expectEndOfLine();
                if (root != null) {
                    System.out.println(root);
                }
                //expression is called repeatedly as long as there are more expressions
                while (root != null) {
                    if (!parserTokens.isEmpty()) {
                        root = expression();
                        if (root != null) {
                            expectEndOfLine();
                            System.out.println(root);
                        }
                    } else {
                        root = null;
                    }
                }

                //dedent is then expected
                if (!parserTokens.isEmpty()) {
                    token = matchAndRemove(Token.tokenType.DEDENT);
                }
            }
        }
        //function node is returned
        return new FunctionNode(functionName, params, vars);
    }

    /**
     * method parses incoming parameter variables and adds them to a LinkedList
     *
     * @return a LinkedList containing the parameters of the function
     */
    private LinkedList<VariableNode> parameterDeclarations() {

        LinkedList<VariableNode> parameterList = new LinkedList<VariableNode>();

        LinkedList<String> varNameHolder = new LinkedList<String>();

        //function will loop while there are still identifiers or var keywords in the parentheses
        while (peek(0).getTokenType() == Token.tokenType.IDENTIFIER ||
                peek(0).getTokenType() == Token.tokenType.VAR) {
            //checks if next token is a var or a constant parameter
            if (peek(0).getTokenType() == Token.tokenType.IDENTIFIER) {
                Token token = matchAndRemove(Token.tokenType.IDENTIFIER);

                varNameHolder.add(token.getTokenContents());

                //loops while commas are present and collects the constant names into a linked list of strings
                while (peek(0).getTokenType() == Token.tokenType.COMMA) {
                    matchAndRemove(Token.tokenType.COMMA);
                    token = matchAndRemove(Token.tokenType.IDENTIFIER);
                    if (token != null) {
                        varNameHolder.add(token.getTokenContents());
                    } else {
                        throw new SyntaxErrorException("Identifier expected after comma in parameters at line " + peek(0).getTokenLine());
                    }
                }
                //expects a colon after parameter names are declared
                token = matchAndRemove(Token.tokenType.COLON);
                //code inside this if statement determines the type of the parameters and loops to create new variable Nodes
                if (token != null) {
                    token = matchAndRemove(Token.tokenType.INTEGER);
                    if (token != null) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new IntegerNode(), false));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.REAL);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new RealNode(), false));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.BOOLEAN);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new BooleanNode(), false));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.CHARACTERLITERAL);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new CharacterNode(), false));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.STRINGLITERAL);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new StringNode(), false));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.ARRAY);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        token = matchAndRemove(Token.tokenType.OF);
                        if (token != null) {
                            token = matchAndRemove(peek(0).getTokenType());

                            switch (token.getTokenType()) {
                                case INTEGER:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new IntegerNode(), -1, -1, false));
                                    }
                                    break;
                                case REAL:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new RealNode(), -1, -1, false));
                                    }
                                    break;
                                case BOOLEAN:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new BooleanNode(), -1, -1, false));
                                    }
                                    break;
                                case CHARACTERLITERAL:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new CharacterNode(), -1, -1, false));
                                    }
                                    break;
                                case STRINGLITERAL:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new StringNode(), -1, -1, false));
                                    }
                                    break;
                                default:
                                    throw new SyntaxErrorException("no array datatype specified at line " + peek(0).getTokenLine());

                            }
                        }
                    }
                } else {
                    throw new SyntaxErrorException("colon expected after parameter identifier declarations at line " + peek(0).getTokenLine());
                }
                token = matchAndRemove(Token.tokenType.SEMICOLON);
                // if next token is var, method performs the same operations it would for constants,
                //except sets changeable to true
            } else if (peek(0).getTokenType() == Token.tokenType.VAR) {

                matchAndRemove(Token.tokenType.VAR);

                Token token = matchAndRemove(Token.tokenType.IDENTIFIER);

                varNameHolder.add(token.getTokenContents());

                while (peek(0).getTokenType() == Token.tokenType.COMMA) {
                    matchAndRemove(Token.tokenType.COMMA);
                    token = matchAndRemove(Token.tokenType.IDENTIFIER);
                    if (token != null) {
                        varNameHolder.add(token.getTokenContents());
                    }
                }
                token = matchAndRemove(Token.tokenType.COLON);
                if (token != null) {
                    token = matchAndRemove(Token.tokenType.INTEGER);
                    if (token != null) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new IntegerNode(), true));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.REAL);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new RealNode(), true));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.BOOLEAN);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new BooleanNode(), true));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.CHARACTERLITERAL);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new CharacterNode(), true));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.STRINGLITERAL);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        while (!varNameHolder.isEmpty()) {
                            parameterList.add(new VariableNode(varNameHolder.remove(), new StringNode(), true));
                        }
                    } else {
                        token = matchAndRemove(Token.tokenType.ARRAY);
                    }

                    if (token != null && !varNameHolder.isEmpty()) {
                        token = matchAndRemove(Token.tokenType.OF);
                        if (token != null) {
                            token = matchAndRemove(peek(0).getTokenType());

                            switch (token.getTokenType()) {
                                case INTEGER:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new IntegerNode(), -1, -1, true));
                                    }
                                    break;
                                case REAL:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new RealNode(), -1, -1, true));
                                    }
                                    break;
                                case BOOLEAN:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new BooleanNode(), -1, -1, true));
                                    }
                                    break;
                                case CHARACTERLITERAL:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new CharacterNode(), -1, -1, true));
                                    }
                                    break;
                                case STRINGLITERAL:
                                    while (!varNameHolder.isEmpty()) {
                                        parameterList.add(new VariableNode(varNameHolder.remove(), new StringNode(), -1, -1, true));
                                    }
                                    break;
                                default:
                                    throw new SyntaxErrorException("no array datatype specified at line " + peek(0).getTokenLine());

                            }
                        }
                    }
                }
                //expects a semi colon at the end of each parameter set
                token = matchAndRemove(Token.tokenType.SEMICOLON);
            } else {
                throw new SyntaxErrorException("Invalid Parameters at line " + peek(0).getTokenLine());
            }

        }
        //returns a list of parameters
        return parameterList;
    }

    /**
     * method used to parse constants
     *
     * @return a LinkedList containing the constants listed on the line
     */
    private LinkedList<VariableNode> constants() {
        LinkedList<VariableNode> constantsList = new LinkedList<VariableNode>();
        Token token;

        do {
            //Identifier expected after constants declaration, if not found, expcetion is thrown
            token = matchAndRemove(Token.tokenType.IDENTIFIER);
            if (token == null) {
                throw new SyntaxErrorException("invalid constant declaration at line " + peek(0).getTokenLine());
            } else {
                //identifier is copied to a string for the name of the variable
                String constName = token.getTokenContents();
                //equals expected after variable name, if not found, exception is thrown
                token = matchAndRemove(Token.tokenType.EQUALS);
                if (token == null) {
                    throw new SyntaxErrorException("no equals found at constant declaration at line " + peek(0).getTokenLine());
                } else {
                    //Variable type is determined using this switch case and a new variable node is created and added to the list
                    switch (peek(0).getTokenType()) {
                        case MINUS:
                            token = matchAndRemove(Token.tokenType.NUMBER);
                            if (token != null) {
                                if (Float.parseFloat(token.getTokenContents()) % 1 == 0) {
                                    constantsList.add(
                                            new VariableNode(
                                                    constName,
                                                    new IntegerNode(),
                                                    new IntegerNode(Integer.parseInt(token.getTokenContents()) -
                                                            (2 * (Integer.parseInt(token.getTokenContents()))))));
                                } else {
                                    constantsList.add(
                                            new VariableNode(
                                                    constName,
                                                    new RealNode(),
                                                    new RealNode(Float.parseFloat(token.getTokenContents()) -
                                                            (2 * (Float.parseFloat(token.getTokenContents()))))));
                                }

                            }
                            break;
                        case NUMBER:
                            token = matchAndRemove(Token.tokenType.NUMBER);
                            if (token != null) {
                                if (Float.parseFloat(token.getTokenContents()) % 1 == 0) {
                                    constantsList.add(
                                            new VariableNode(
                                                    constName,
                                                    new IntegerNode(),
                                                    new IntegerNode(Integer.parseInt(token.getTokenContents()))));
                                } else {
                                    constantsList.add(
                                            new VariableNode(
                                                    constName,
                                                    new RealNode(),
                                                    new RealNode(Float.parseFloat(token.getTokenContents()))));
                                }
                            }
                            break;
                        case STRINGLITERAL:
                            token = matchAndRemove(Token.tokenType.STRINGLITERAL);
                            if (token != null) {
                                constantsList.add(
                                        new VariableNode(
                                                constName,
                                                new StringNode(),
                                                new StringNode(token.getTokenContents())));
                            }
                            break;
                        case CHARACTERLITERAL:
                            token = matchAndRemove(Token.tokenType.CHARACTERLITERAL);
                            if (token != null) {
                                constantsList.add(
                                        new VariableNode(
                                                constName,
                                                new CharacterNode(),
                                                new CharacterNode(token.getTokenContents().charAt(0))));
                            }
                            break;
                        case TRUE:
                        case FALSE:
                            token = matchAndRemove(Token.tokenType.TRUE);
                            if (token != null) {
                                constantsList.add(
                                        new VariableNode(
                                                constName,
                                                new BooleanNode(),
                                                new BooleanNode(true)));
                            } else {
                                token = matchAndRemove(Token.tokenType.FALSE);
                                if (token != null) {
                                    constantsList.add(
                                            new VariableNode(
                                                    constName,
                                                    new BooleanNode(),
                                                    new BooleanNode(false)));
                                }
                            }
                            break;
                        default:
                            throw new SyntaxErrorException("invalid data type found at line " + peek(0).getTokenLine());


                    }
                }
            }
            //if comma is found after variable node is added, another constant is expected, so the loop loops again
            token = matchAndRemove(Token.tokenType.COMMA);

        } while (token != null);
        //list of constants is returned
        return constantsList;
    }

    /**
     * method used to parse the variables of the function
     *
     * @return a linked list of the variables listed on the line
     */
    private LinkedList<VariableNode> variables() {
        LinkedList<VariableNode> variablesList = new LinkedList<VariableNode>();
        LinkedList<String> variableNames = new LinkedList<String>();
        //in case of array 2 index values are declared
        int from = -1;
        int to = -1;

        //identifier is expected after variables keyword
        Token token = matchAndRemove(Token.tokenType.IDENTIFIER);


        if (token != null) {
            //first variable name is added to the list
            variableNames.add(token.getTokenContents());
            //if there is a comma after the first variable, loop loops until there is no more commas
            while (peek(0).getTokenType() == Token.tokenType.COMMA) {
                matchAndRemove(Token.tokenType.COMMA);
                token = matchAndRemove(Token.tokenType.IDENTIFIER);
                //varible name is added to the list
                if (token != null) {
                    variableNames.add(token.getTokenContents());
                } else {
                    throw new SyntaxErrorException("Identifier expected after comma in local variable declaration at line " + peek(0).getTokenLine());
                }
            }
            //colon is expected after variable names
            token = matchAndRemove(Token.tokenType.COLON);
            if (token != null) {
                //code below determines the varible type and loops to add new varible nodes to the list
                token = matchAndRemove(Token.tokenType.INTEGER);
                if (token != null) {
                    while (!variableNames.isEmpty()) {
                        variablesList.add(new VariableNode(
                                variableNames.remove(),
                                new IntegerNode(),
                                true));
                    }
                } else {
                    token = matchAndRemove(Token.tokenType.REAL);
                }

                if (token != null && !variableNames.isEmpty()) {
                    while (!variableNames.isEmpty()) {
                        variablesList.add(new VariableNode(
                                variableNames.remove(),
                                new RealNode(),
                                true));
                    }
                } else {
                    token = matchAndRemove(Token.tokenType.BOOLEAN);
                }

                if (token != null && !variableNames.isEmpty()) {
                    while (!variableNames.isEmpty()) {
                        variablesList.add(new VariableNode(
                                variableNames.remove(),
                                new BooleanNode(),
                                true));
                    }
                } else {
                    token = matchAndRemove(Token.tokenType.CHARACTERLITERAL);
                }

                if (token != null && !variableNames.isEmpty()) {
                    while (!variableNames.isEmpty()) {
                        variablesList.add(new VariableNode(variableNames.remove(),
                                new CharacterNode(),
                                true));
                    }
                } else {
                    token = matchAndRemove(Token.tokenType.STRINGLITERAL);
                }

                if (token != null && !variableNames.isEmpty()) {
                    while (!variableNames.isEmpty()) {
                        variablesList.add(new VariableNode(variableNames.remove(),
                                new StringNode(),
                                true));
                    }
                } else {
                    token = matchAndRemove(Token.tokenType.ARRAY);
                }

                if (token != null && !variableNames.isEmpty() && Objects.equals(variableNames.peekFirst(), variableNames.peekLast())) {

                    token = matchAndRemove(Token.tokenType.FROM);
                    if (token != null) {
                        token = matchAndRemove(Token.tokenType.NUMBER);
                        if (token != null) {
                            from = Integer.parseInt(token.getTokenContents());
                        } else {
                            throw new SyntaxErrorException("integer is expected after from at line " + peek(0).getTokenLine());
                        }

                    } else {
                        throw new SyntaxErrorException("from expected after array is declared at line " + peek(0).getTokenLine());
                    }

                    token = matchAndRemove(Token.tokenType.TO);
                    if (token != null) {
                        token = matchAndRemove(Token.tokenType.NUMBER);
                        if (token != null) {
                            to = Integer.parseInt(token.getTokenContents());
                        } else {
                            throw new SyntaxErrorException("integer is expected after to at line " + peek(0).getTokenLine());
                        }
                    } else {
                        throw new SyntaxErrorException("to is expected after from index is declared at line " + peek(0).getTokenLine());
                    }

                    token = matchAndRemove(Token.tokenType.OF);
                    if (token != null) {
                        switch (peek(0).getTokenType()) {
                            case INTEGER:
                                token = matchAndRemove(Token.tokenType.INTEGER);
                                variablesList.add(new VariableNode(variableNames.remove(), new IntegerNode(), from, to, true));
                                break;
                            case REAL:
                                token = matchAndRemove(Token.tokenType.REAL);
                                variablesList.add(new VariableNode(variableNames.remove(), new RealNode(), from, to, true));
                                break;
                            case BOOLEAN:
                                token = matchAndRemove(Token.tokenType.BOOLEAN);
                                variablesList.add(new VariableNode(variableNames.remove(), new BooleanNode(), from, to, true));
                                break;
                            case CHARACTERLITERAL:
                                token = matchAndRemove(Token.tokenType.CHARACTERLITERAL);
                                variablesList.add(new VariableNode(variableNames.remove(), new CharacterNode(), from, to, true));
                                break;
                            case STRINGLITERAL:
                                token = matchAndRemove(Token.tokenType.STRINGLITERAL);
                                variablesList.add(new VariableNode(variableNames.remove(), new StringNode(), from, to, true));
                                break;

                        }
                    }
                } else if (variablesList.isEmpty()) {
                    throw new SyntaxErrorException("no valid data type found after variable at line " + peek(0).getTokenLine());
                }
            } else {
                throw new SyntaxErrorException("':' is expected after varibles are declared at line " + peek(0).getTokenLine());
            }
        } else {
            throw new SyntaxErrorException("Identifier expected after var keyword at line " + peek(0).getTokenLine());
        }
        //returns list of variables
        return variablesList;
    }
}
