package com.lsa.eval.arithmetic.lexer.impl;

import java.util.HashMap;
import java.util.Map;

import com.lsa.eval.arithmetic.lexer.ILexer;
import com.lsa.eval.arithmetic.lexer.IToken;
import com.lsa.eval.arithmetic.lexer.TokenType;

public final class Lexer implements ILexer {
    private final String inputBuffer;
    private int pos = 0;
    private Map<Integer, TokenType> simpleTokenCharToType = new HashMap<Integer, TokenType>();
    
    public Lexer(String inputBuffer) {
        this.inputBuffer = inputBuffer;
        simpleTokenCharToType.put((int)'+', TokenType.PLUS);
        simpleTokenCharToType.put((int)'-', TokenType.MINUS);
        simpleTokenCharToType.put((int)'*', TokenType.MULTIPLY);
        simpleTokenCharToType.put((int)'/', TokenType.DIVIDE);
        simpleTokenCharToType.put((int)'\\', TokenType.DIVIDE);
        simpleTokenCharToType.put((int)'^', TokenType.POWER);
        simpleTokenCharToType.put((int)'(', TokenType.LPARENTH);
        simpleTokenCharToType.put((int)')', TokenType.RPARENTH);
    }
    
    @Override
    public IToken nextToken() {
        skipWhitespace();
        if (isEnd()) {
            return new Token(inputBuffer, TokenType.END, inputBuffer.length(), 0);
        }
        IToken token = null;
        int c = currentChar();
        if (Character.isDigit(c)) {
            int start = pos;
            int end = pos;
            while(nextChar() && Character.isDigit(currentChar())) {
                end++;
            }
            token = new Token(inputBuffer, TokenType.CONSTANT, start, end - start + 1);
        }
        else if (Character.isLetter(c)){
            int start = pos;
            int end = pos;
            while(nextChar() && (Character.isDigit(currentChar()) || Character.isLetter(currentChar()))) {
                end++;
            }
            token = new Token(inputBuffer, TokenType.VARIABLE, start, end - start + 1);
        }
        else {
            TokenType tokenType = simpleTokenCharToType.get(c);
            if (tokenType != null) {
                token = createSimpleToken(tokenType);
            }
        }
        
        if (token == null) {
            token = new Token(inputBuffer, TokenType.ERROR, pos, 1);
            nextChar();
        }
        
        return token;
    }

    public Token createSimpleToken(final TokenType tokenType) {
        try{
            return new Token(inputBuffer, tokenType, pos, 1);
        }
        finally{
            nextChar();            
        }
    }

    private void skipWhitespace() {
        while(!isEnd()) {
            int c = currentChar();
            if (!Character.isWhitespace(c)) {
                break;
            }
            nextChar();
        }
    }

    private boolean nextChar() {
        pos++;
        return !isEnd();
    }

    private boolean isEnd() {
        return pos >= inputBuffer.length();
    }

    private int currentChar() {
        return inputBuffer.charAt(pos);
    }
}