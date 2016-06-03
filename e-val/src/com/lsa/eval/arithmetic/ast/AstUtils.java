package com.lsa.eval.arithmetic.ast;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lsa.eval.arithmetic.OperationType;
import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.lexer.impl.Lexer;
import com.lsa.eval.arithmetic.parser.IParser;
import com.lsa.eval.arithmetic.parser.impl.ClassicParser;
import com.lsa.eval.util.Pair;

public final class AstUtils {
	public final static String CRLF = System.getProperty("line.separator");
	
	public static Number e_val(String expr) {
		ILexer lexer = new Lexer(expr);
		IParser parser = new ClassicParser(lexer);
		INode node = parser.parse();
		
		return e_val(node);
	}

	public static Number e_val(INode node) {
		EvalVisitor v = new EvalVisitor();
		return node.accept(v, null).doubleValue();
	}
	
    public static String treeToString(INode tree) {
        return null;
    }
    
    public static void toTgf(INode root, Writer w) throws IOException {
    	ReapNodeEdgesVisitor reapGraphVisitor = new ReapNodeEdgesVisitor();
    	root.accept(reapGraphVisitor, null);
    	
    	Map<INode, Integer> node2Id = new HashMap<INode, Integer>();
    	
    	ToStringVisitor v = new ToStringVisitor();
    	int id = 0;
    	for (INode n : reapGraphVisitor.vertices) {
    		n.accept(v, null);
    		node2Id.put(n, ++id);
    		w.write(String.format("%s %s%s", id, v.result, CRLF));
		}
    	
    	w.write(String.format("%s%s", "#", CRLF));
    	
    	for (Pair<INode, INode> e : reapGraphVisitor.edges) {
    		w.write(String.format("%s %s%s", node2Id.get(e.getFirst()), node2Id.get(e.getSecond()), CRLF));
		}
    }
    
    private static class ReapNodeEdgesVisitor extends SimpleVisitor{
    	List<INode> vertices = new ArrayList<INode>();
    	List<Pair<INode,INode>> edges = new ArrayList<Pair<INode,INode>>();
    	
		@Override
		public boolean preVisit(INode n) {
			vertices.add(n);
			return true;
		}

		@Override
		protected boolean preVisitChild(INode parent, INode child) {
			edges.add(Pair.create(parent, child));
			return true;
		}
    }
    
    private static class ToStringVisitor extends Visitor<Void, Void> {
    	private String result;
    	
		@Override
		public Void visit(IBinaryOperation n, Void p) {
			result = String.format("%s", opToString(n.getOperationType()));
			return null;
		}
		private String opToString(OperationType opType) {
			switch (opType) {
			case PLUS:
				return "+";
			case BINARY_MINUS:
			case UNARY_MINUS:
				return "-";
			case DIVIDE:
				return "/";
			case MULTIPLY:
				return "*";
			case POWER:
				return "^";
			default:
				return null;
			}
		}
		@Override
		public Void visit(IUnaryOperation n, Void p) {
			result = String.format("%s", opToString(n.getOperationType()));
			return null;
		}

		@Override
		public Void visit(IVariable n, Void p) {
			result = String.format("$%s$", n.getName());
			return null;
		}
		
		@Override
		public Void visit(IConstant n, Void p) {
			result = String.format("%s", n.getValue());
			return null;
		}
    }
    
    private AstUtils() {
    }
}