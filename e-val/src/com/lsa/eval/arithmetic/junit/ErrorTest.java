package com.lsa.eval.arithmetic.junit;

import org.junit.Assert;
import org.junit.Test;

import com.lsa.eval.arithmetic.ParseException;
import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.parser.IParser;

public class ErrorTest extends TestBase {
	@Test
	public void test1() {
		expectError("+1", 0);
		expectError("1+", 2);
		expectError("1+++", 2);
		expectError("50   50", 5);
		expectError("1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1*+1+1", 40);
		expectError("(8+5+2*1)*(2-42/2)-(8+5+2*1+44)*((2-2*42-44)", 44);
	}
	
	private void expectError(String expr, int pos) {
		try {
			ILexer lexer = createLexer(expr);
			IParser parser = createParser(lexer);
			parser.parse();
		} catch (ParseException e) {
			Assert.assertTrue(e.getPosition() == pos);
			return;
		}
		Assert.assertTrue(false);
	}
}
