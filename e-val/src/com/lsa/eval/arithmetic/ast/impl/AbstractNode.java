package com.lsa.eval.arithmetic.ast.impl;

import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.ast.Visitor;

public abstract class AbstractNode implements INode {
	public <R,P> R accept(Visitor<R,P> v, P param) {
		// begin with the generic pre-visit
		R result = null;
        if (v.preVisit(this)) {
            // dynamic dispatch to internal method for type-specific visit/endVisit
        	result = doAccept(v, param);
        }
        // end with the generic post-visit
        v.postVisit(this);
        
        return result;
	}

	protected abstract <R,P> R doAccept(Visitor<R,P> v, P param);
}