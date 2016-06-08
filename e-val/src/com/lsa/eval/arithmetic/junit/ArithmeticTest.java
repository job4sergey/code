package com.lsa.eval.arithmetic.junit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lsa.eval.arithmetic.ast.EvalVisitor;
import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.parser.IParser;

public class ArithmeticTest extends TestBase {
	private ScriptEngineManager m;
	private ScriptEngine e;

	@Before
	public void setup() {
		m = new ScriptEngineManager();
        e = m.getEngineByExtension("js");
	}
	@Test
	public void test() {
		try {
			testExpressionByJs("1");
			testExpressionByJs("1+1");
			testExpressionByJs("1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1");
			testExpressionByJs("(1+1)*2");
			testExpressionByJs("(1+1)*(2-50)");
			testExpressionByJs("(1+1)*(2-50)+((1+1)*(2-50)-(1+1)*2)");
			testExpressionByJs("(8+5+2*1)*(2-2*42)");
			testExpressionByJs("(8+5+2*1)*(2-42/2)");
			testExpressionByJs("(8+5+2*1)*(2-42/2)-(8+5+2*1+44)*(2-2*42-44)");
			testExpression("(8+5+2*1)*(2-42/2)-(8+5+2*1+44)*(2-2*42-44)", 7149.0);
			testExpression("(8+5+2*1)*2^2", 60.0);
			testExpressionByJs("1+2*3+4/2+3*3");
			testExpressionByJs("2+2+(2*-2+4)");
			
		} catch (ScriptException e) {
			Assert.assertTrue(false);
		}
	}
	
	private void testExpressionByJs(String expr) throws ScriptException {
		Assert.assertEquals(evalByJs(expr), e_val(expr));
	}
	
	private void testExpression(String expr, Double expected) throws ScriptException {
		Assert.assertEquals(expected, e_val(expr));
	}
	
	private Number evalByJs(String expr) throws ScriptException {
		Number n = (Number)e.eval(expr);
		return n.doubleValue();
	}
	
	private Double e_val(String expr) throws ScriptException {
		EvalVisitor v = new EvalVisitor();
		
		ILexer lexer = createLexer(expr);
		IParser parser = createParser(lexer);
		
		INode node = parser.parse();
		return node.accept(v, null).doubleValue();
	}
}
