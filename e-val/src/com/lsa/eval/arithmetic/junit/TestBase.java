package com.lsa.eval.arithmetic.junit;

import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.lexer.impl.Lexer;
import com.lsa.eval.arithmetic.parser.IParser;
import com.lsa.eval.arithmetic.parser.impl.ClassicParser;

public abstract class TestBase {
	protected ILexer createLexer(String expr) {
        return new Lexer(expr);
    }
    
	protected IParser createParser(ILexer lexer) {
        return new ClassicParser(lexer);
    }
	
	protected String positionMessage(int pos) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < pos; i++) {
			sb.append(' ');
		}
		sb.append('^');
		return sb.toString();
	}
}
