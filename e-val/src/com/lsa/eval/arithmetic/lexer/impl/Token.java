package com.lsa.eval.arithmetic.lexer.impl;

import com.lsa.eval.arithmetic.lexer.IToken;
import com.lsa.eval.arithmetic.lexer.TokenType;

public final class Token implements IToken {
    private final String inputBuffer;
    private final TokenType tokenType;
    private final int offset;
    private final int length;
    
    public Token(String inputBuffer, TokenType tokenType, int offset,
            int length) {
        this.inputBuffer = inputBuffer;
        this.tokenType = tokenType;
        this.offset = offset;
        this.length = length;
    }

    @Override
    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String getText() {
        if (offset < 0) {
            return "";
        }
        return inputBuffer.subSequence(offset, offset + length).toString();
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Token [text=" + getText() + ", tokenType=" + tokenType
                + ", offset=" + offset + ", length=" + length + "]";
    }
}
