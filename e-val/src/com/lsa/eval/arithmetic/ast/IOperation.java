package com.lsa.eval.arithmetic.ast;

import com.lsa.eval.arithmetic.OperationType;

public interface IOperation extends INode{
    OperationType getOperationType();
}