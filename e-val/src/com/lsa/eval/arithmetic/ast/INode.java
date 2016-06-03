package com.lsa.eval.arithmetic.ast;

import java.util.List;

public interface INode {
	List<INode> getChildren();
	<R,P> R accept(Visitor<R,P> v, P param);
}