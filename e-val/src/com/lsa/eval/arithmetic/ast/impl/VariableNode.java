package com.lsa.eval.arithmetic.ast.impl;

import java.util.Collections;
import java.util.List;

import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.ast.IVariable;
import com.lsa.eval.arithmetic.ast.Visitor;

public final class VariableNode extends AbstractNode implements IVariable {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
	public List<INode> getChildren() {
		return Collections.emptyList();
	}

    @Override
	protected <R,P> R doAccept(Visitor<R,P> v, P param) {
		return v.visit(this, param);
	}
}