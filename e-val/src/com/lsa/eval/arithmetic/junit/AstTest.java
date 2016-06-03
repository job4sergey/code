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

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class AstTest extends TestBase {
	 @Test
	 public void test() throws IOException {
		String expr = "(8+5+2*1)*(2-42/2)-(8+5+2*1+44)*((2-2*42-44))";
		ILexer lexer = createLexer(expr);
		IParser parser = createParser(lexer);
		try {
			final INode root = parser.parse();
			File serializedTree = new File("data/gen/simple/ast.tgf");
			BufferedWriter bw = new BufferedWriter(new FileWriter(serializedTree));
			try {
				AstUtils.toTgf(root, bw);
			} finally {
				bw.close();
			}
			Assert.assertEquals(7149.0, AstUtils.e_val(root));
			Assert.assertEquals(readFileToString(new File("data/simple/ast.tgf")), readFileToString(serializedTree));
		} catch (ParseException e) {
			String msg = String.format("%1$s%2$s%1$s%3$s%1$s%4$s%1$s",
					AstUtils.CRLF, expr, positionMessage(e.getPosition()),
					e.getMessage());
			System.out.println(msg);
			Assert.assertFalse(msg, true);
		}
	 }

	private String readFileToString(File file) throws IOException {
		return new String(readAllBytes(get(file.toURI())));
	}
}