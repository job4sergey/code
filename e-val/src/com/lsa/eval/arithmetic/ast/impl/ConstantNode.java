package com.lsa.eval.arithmetic.ast.impl;

import java.util.Collections;
import java.util.List;

import com.lsa.eval.arithmetic.ast.IConstant;
import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.ast.Visitor;

public class ConstantNode extends AbstractNode implements IConstant {
	private final Number value;

	public ConstantNode(Number value) {
		this.value = value;
	}

	@Override
	public List<INode> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public Number getValue() {
		return value;
	}

	@Override
	protected <R,P> R doAccept(Visitor<R,P> v, P param) {
		return v.visit(this, param);
	}
}
