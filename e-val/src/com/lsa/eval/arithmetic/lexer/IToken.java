package com.lsa.eval.arithmetic.lexer;

public interface IToken {
    TokenType getTokenType();
    String getText();
    int getOffset();
    int getLength();
}
