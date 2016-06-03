package com.lsa.eval.arithmetic.junit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.lsa.eval.arithmetic.ParseException;
import com.lsa.eval.arithmetic.ast.AstUtils;
import com.lsa.eval.arithmetic.ast.INode;
import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.parser.IParser;

public class SimpleTest extends TestBase{
	@Test
    public void parseToAstTest() throws IOException{
//        String expr = "(8+115-ID*1+6/3-+88/4)*2^2";
//		String expr = "42-(2+2+(2*-2+4))+2";
//		String expr = "2+2+(2-4*3)";
		String expr = "(8+5+2*1)*(2-42/2)-(8+5+2*1+44)*((2-2*42-44))";
        ILexer lexer = createLexer(expr);
        IParser parser = createParser(lexer);
        try {
            final INode root = parser.parse();
            File f = new File("data/gen/simple/ast.tgf");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			try{
				AstUtils.toTgf(root, bw);
			}
			finally{
				bw.close();
			}
			System.out.println(f.getAbsolutePath());
			System.out.println(AstUtils.e_val(root));
//            final String prettyTree = AstUtils.treeToString(root);
//            Assert.assertEquals("", prettyTree);
        } catch (ParseException e) {
			String msg = String.format("%1$s%2$s%1$s%3$s%1$s%4$s%1$s",AstUtils.CRLF, expr,
					positionMessage(e.getPosition()), 
					e.getMessage());
			System.out.println(msg);
			Assert.assertFalse(msg,
					true);
        }
    }
}