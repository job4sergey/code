package com.lsa.eval.arithmetic.ast;

public interface IBinaryOperation extends IOperation {
    INode getFirstOperand();
    INode getSecondOperand();
}