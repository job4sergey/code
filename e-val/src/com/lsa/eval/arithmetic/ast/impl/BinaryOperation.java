package com.lsa.eval.arithmetic.ast.impl;

import java.util.Arrays;
import java.util.List;

import com.lsa.eval.arithmetic.OperationType;
import com.lsa.eval.arithmetic.ast.IBinaryOperation;
import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.ast.Visitor;

public class BinaryOperation extends AbstractNode implements IBinaryOperation {
    private final OperationType operationType;
    private INode[] operands = new INode[2];
    private int operandIndex = 0;
    
    public BinaryOperation(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    public void attachOperand(INode node) {
        operands[operandIndex++] = node;
    }

    @Override
    public INode getFirstOperand() {
        return operands[0];
    }

    @Override
    public INode getSecondOperand() {
        return operands[1];
    }

	@Override
	public List<INode> getChildren() {
		return Arrays.asList(getFirstOperand(), getSecondOperand());
	}
	
	@Override
	protected <R,P> R doAccept(Visitor<R,P> v, P param) {
		return v.visit(this, param);
	}
}