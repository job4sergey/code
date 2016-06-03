package com.lsa.eval.arithmetic.lexer;

public enum TokenType {
        CONSTANT, /* 651673 */
        VARIABLE, /* theA */
        PLUS, /* + */
        MINUS, /* - */
        MULTIPLY, /* * */
        DIVIDE, /* / */
        POWER, /* ^ */
        LPARENTH, /* ( */
        RPARENTH, /* ) */
        ERROR, // meta-symbol for unexpected character
        END // meta-symbol for END-OF-EXPRESSION 
}
