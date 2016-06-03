package com.lsa.eval.arithmetic.junit;

import org.junit.Assert;
import org.junit.Test;

import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.lexer.IToken;
import com.lsa.eval.arithmetic.lexer.TokenType;

public class LexerTest extends TestBase{
    @Test
    public void lexerTest() {
        String expr = "(8+115-ID*1+6/3-88/4)*2^2";
        ILexer lexer = createLexer(expr);
        String[] expected = {"LPARENTH,(,0,1","CONSTANT,8,1,1","PLUS,+,2,1","CONSTANT,115,3,3","MINUS,-,6,1",
                "VARIABLE,ID,7,2","MULTIPLY,*,9,1","CONSTANT,1,10,1","PLUS,+,11,1","CONSTANT,6,12,1","DIVIDE,/,13,1"
                ,"CONSTANT,3,14,1","MINUS,-,15,1","CONSTANT,88,16,2","DIVIDE,/,18,1","CONSTANT,4,19,1","RPARENTH,),20,1","MULTIPLY,*,21,1","CONSTANT,2,22,1","POWER,^,23,1","CONSTANT,2,24,1"};
        int i = 0;
        IToken token = lexer.nextToken();
        while(token.getTokenType() != TokenType.END) {
            Assert.assertTrue(i < expected.length);
            Assert.assertEquals(expected[i++], toString(token));
            token = lexer.nextToken();
        }
        Assert.assertTrue(i == expected.length);
    }
    
    private String toString(IToken t) {
        if (t.getTokenType() == TokenType.END) {
            return "END";
        }
        return String.format("%s,%s,%d,%d", t.getTokenType(), t.getText(), t.getOffset(), t.getLength()); 
    }
}
