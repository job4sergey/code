package com.lsa.eval.arithmetic.parser;

import com.lsa.eval.arithmetic.ParseException;
import com.lsa.eval.arithmetic.ast.INode;

public interface IParser {
    INode parse() throws ParseException;
}