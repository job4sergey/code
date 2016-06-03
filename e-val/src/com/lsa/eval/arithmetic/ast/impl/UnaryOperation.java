package com.lsa.eval.arithmetic.ast.impl;

import java.util.Arrays;
import java.util.List;

import com.lsa.eval.arithmetic.OperationType;
import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.ast.IUnaryOperation;
import com.lsa.eval.arithmetic.ast.Visitor;

public final class UnaryOperation extends AbstractNode implements IUnaryOperation {
    private final OperationType operationType;
    private INode operand;

    public UnaryOperation(OperationType operationType, INode operand) {
        this.operationType = operationType;
        this.operand = operand;
    }

    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public INode getOperand() {
        return operand;
    }

    public void setOperand(INode operand) {
        this.operand = operand;
    }
    
    @Override
	public List<INode> getChildren() {
		return Arrays.asList(getOperand());
	}

	@Override
	protected <R,P> R doAccept(Visitor<R,P> v, P param) {
		return v.visit(this, param);
	}
}