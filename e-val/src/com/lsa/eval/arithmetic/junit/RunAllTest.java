package com.lsa.eval.arithmetic.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LexerTest.class,
	ArithmeticTest.class,
	ErrorTest.class,
})
public class RunAllTest{
}

