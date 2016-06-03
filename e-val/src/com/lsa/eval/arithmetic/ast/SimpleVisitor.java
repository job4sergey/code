package com.lsa.eval.arithmetic.ast;


public abstract class SimpleVisitor extends Visitor<Void, Void> {
	@Override
	public Void visit(IBinaryOperation n, Void param) {
		visitChildren(n,param);
		return null;
	}

	@Override
	public Void visit(IUnaryOperation n, Void param) {
		visitChildren(n,param);
		return null;
	}

	@Override
	public Void visit(IVariable n, Void param) {
		visitChildren(n,param);
		return null;
	}

	@Override
	public Void visit(IConstant n, Void param) {
		visitChildren(n,param);
		return null;
	}
	
	protected void visitChildren(INode parent, Void param) {
		for (INode n : parent.getChildren()) {
			if (preVisitChild(parent, n)) {
				n.accept(this, null);
			}
		}
	}
	
	protected abstract boolean preVisitChild(INode parent, INode child);
}
