package com.lsa.eval.arithmetic.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.lsa.eval.arithmetic.OperationType;
import com.lsa.eval.arithmetic.ParseException;
import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.ast.IVariable;
import com.lsa.eval.arithmetic.ast.impl.BinaryOperation;
import com.lsa.eval.arithmetic.ast.impl.ConstantNode;
import com.lsa.eval.arithmetic.ast.impl.UnaryOperation;
import com.lsa.eval.arithmetic.ast.impl.VariableNode;
import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.lexer.IToken;
import com.lsa.eval.arithmetic.lexer.TokenType;
import com.lsa.eval.arithmetic.parser.IParser;

public final class ClassicParser implements IParser {
    private final ILexer lexer;
    private final Map<TokenType, OperationType> tokenTypeToOperationType = new HashMap<TokenType, OperationType>();
    private IToken currentToken;
    
    public ClassicParser(ILexer lexer) {
        this.lexer = lexer;
        tokenTypeToOperationType.put(TokenType.PLUS, OperationType.PLUS);
        tokenTypeToOperationType.put(TokenType.MINUS, OperationType.BINARY_MINUS);
        tokenTypeToOperationType.put(TokenType.MULTIPLY, OperationType.MULTIPLY);
        tokenTypeToOperationType.put(TokenType.DIVIDE, OperationType.DIVIDE);
        tokenTypeToOperationType.put(TokenType.POWER, OperationType.POWER);
    }
    
    @Override
    public INode parse() throws ParseException {
    	nextToken();
        INode tree = expression();
        expect(TokenType.END);
        return tree;
    }

    private INode expression() throws ParseException{
        INode tree = term();
        while(isNext(TokenType.PLUS) || isNext(TokenType.MINUS)) {
        	expectNotNull(tree, "Expected left part of the binary operator");
            BinaryOperation binaryOp = createBinaryOp(currentToken.getTokenType());
            nextToken();
            INode operand = term();
            tree = attachOperands(binaryOp, tree, operand);
        }
        return tree;
    }
    
    private INode term() {
        INode tree = factor();
        while(isNext(TokenType.MULTIPLY) || isNext(TokenType.DIVIDE)) {
        	expectNotNull(tree, "Expected left part of the binary operator");
            BinaryOperation binaryOp = createBinaryOp(currentToken.getTokenType());
            nextToken();
            INode operand = factor();
            tree = attachOperands(binaryOp, tree, operand);
        }
        return tree;
    }

    private INode factor() {
        INode tree = power();
        while(isNext(TokenType.POWER)) {
        	expectNotNull(tree, "Expected left part of the binary operator");
            BinaryOperation binaryOp = createBinaryOp(currentToken.getTokenType());
            nextToken();
            INode operand = power();
            tree = attachOperands(binaryOp, tree, operand);
        }
        return tree;
    }

    private INode power() {
        INode tree = null;
        if (isNext(TokenType.CONSTANT)) {
        	tree = createConst();
        	nextToken();
        }
        else if (isNext(TokenType.VARIABLE)) {
            tree = createVar();
            nextToken();
        }
        else if (isNext(TokenType.LPARENTH)) {
            nextToken();
            tree = expression();
            expect(TokenType.RPARENTH);
        }
        else if (isNext(TokenType.MINUS)) {
            nextToken();
            tree = factor();
            tree = new UnaryOperation(OperationType.UNARY_MINUS, tree);
        }
            
        return tree;
    }

    private INode createConst() {
		return new ConstantNode(Double.valueOf(currentToken.getText()));
	}

	private IVariable createVar() {
        return new VariableNode(currentToken.getText());
    }

    private INode attachOperands(BinaryOperation binaryOp, INode firstOperand,
            INode secondOperand) {
    	expectNotNull(secondOperand, "Expected right part of the binary operator");
        binaryOp.attachOperand(firstOperand);
        binaryOp.attachOperand(secondOperand);
        return binaryOp;
    }

    private void nextToken() {
        currentToken = lexer.nextToken();
    }

    private BinaryOperation createBinaryOp(TokenType tokenType) {
        return createBinaryOp(tokenTypeToOperationType(tokenType));
    }

    private BinaryOperation createBinaryOp(final OperationType operationType) {
        return new BinaryOperation(operationType);
    }
    
    private OperationType tokenTypeToOperationType(TokenType tokenType) {
        return tokenTypeToOperationType.get(tokenType);
    }

    private boolean isNext(TokenType tokenType) {
    	if (currentToken == null) {
    		return tokenType == TokenType.END;
    	}
        return currentToken.getTokenType() == tokenType;
    }
    
    private void expectNotNull(INode n, String msg) {
    	if (n == null) {
    		errorAtPosition(msg);
    	}
    }

    private void expect(TokenType tokenType) throws ParseException {
        if (!isNext(tokenType)) {
        	TokenType actual = currentToken != null? currentToken.getTokenType() : null;
            errorAtPosition(String.format("Unexpected token: Expected %s, got %s", tokenType, actual));
        }
        nextToken();
    }

    private void errorAtPosition(String msg) throws ParseException{
        String fullMsg = String.format("Error at position %d : %s", currentToken.getOffset(), msg);
        error(fullMsg, currentToken.getOffset());
    }

    private void error(String msg, int pos) throws ParseException{
        throw new ParseException(msg, pos);
    }
}
