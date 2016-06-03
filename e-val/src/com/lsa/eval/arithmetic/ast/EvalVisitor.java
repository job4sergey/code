package com.lsa.eval.arithmetic.ast;

import java.util.Collections;
import java.util.Map;

import com.lsa.eval.arithmetic.OperationType;

public class EvalVisitor extends Visitor<Number, Void> {
	private Map<String, Number> variableName2Value = Collections.emptyMap();
	
	public EvalVisitor(Map<String, Number> variableName2Value) {
		this.variableName2Value = variableName2Value;
	}
	
	public EvalVisitor() {
	}

	@Override
	public Number visit(IBinaryOperation n, Void param) {
		OperationType operation = n.getOperationType();
		Number result = null;
		INode firstOperand = n.getFirstOperand();
		INode secondOperand = n.getSecondOperand();
		switch (operation) {
		case BINARY_MINUS:
			result = firstOperand.accept(this, param).doubleValue() - secondOperand.accept(this, param).doubleValue();
			break;
		case DIVIDE:
			result = firstOperand.accept(this, param).doubleValue() / secondOperand.accept(this, param).doubleValue();
			break;
		case MULTIPLY:
			result = firstOperand.accept(this, param).doubleValue() * secondOperand.accept(this, param).doubleValue();
			break;
		case PLUS:
			result = firstOperand.accept(this, param).doubleValue() + secondOperand.accept(this, param).doubleValue();
			break;
		case POWER:
			result = Math.pow(firstOperand.accept(this, param).doubleValue() , secondOperand.accept(this, param).doubleValue());
			break;
		default:
			throw new RuntimeException("Internal program error");
		}
		return result;
	}

	@Override
	public Number visit(IUnaryOperation n, Void param) {
		Number result = null;
		OperationType operation = n.getOperationType();
		switch (operation) {
		case UNARY_MINUS:
			result = -n.getOperand().accept(this, param).doubleValue();
			break;
		default:
			throw new RuntimeException("Internal program error");
		}
		return result;
	}

	@Override
	public Number visit(IVariable n, Void param) {
		return variableName2Value.get(n.getName());
	}

	@Override
	public Number visit(IConstant constantNode, Void param) {
		return constantNode.getValue();
	}
}
