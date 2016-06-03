package com.lsa.eval.arithmetic.ast;


public abstract class Visitor<R,P> {
    public boolean preVisit(INode node) {
        return true;
    }
    
    public void postVisit(INode node) {
    }
    
    public R visit(IBinaryOperation n, P param) {
    	return null;
    }
    
    public R visit(IUnaryOperation n, P param) {
    	return null;
    }
    
    public R visit(IVariable n, P param) {
    	return null;
    }

	public R visit(IConstant constantNode, P param) {
		return null;
	}
}