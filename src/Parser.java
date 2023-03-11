/**
 * ICSI 311
 * Assignment 3
 * Ryan McSweeney
 * RM483514
 * 2/26/23
 */

import java.util.LinkedList;

public class Parser {

    private LinkedList<Token> parserTokens = new LinkedList<Token>();

    /**
     * constructor for a parser
     * @param tokens list of tokens received from the lexer
     */
    public Parser(LinkedList<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            parserTokens.add(tokens.get(i));
        }
    }

    /**
     * match an remove method used to assist the parser in parsing tokens
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
     * @return null
     */
    public Node parse() {
        while (!parserTokens.isEmpty()) {
            Node root = expression();
            expectEndOfLine();
            System.out.println(root);
        }
        return null;
    }

    /**
     * expression method called by parse which calls term and loops for a plus or a minus
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

}
