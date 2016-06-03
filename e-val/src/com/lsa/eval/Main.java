package com.lsa.eval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.lsa.eval.arithmetic.ast.AstUtils;

public class Main {
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	String expr;
    	if (args.length == 0) {
    		expr = readLine();
    	}
    	else if (args.length == 1) {
    		expr = args[0];
    	}
    	else {
    		System.out.println("Usage: \t\t%scom.lsa.eval.Main expression.%s\t\tcom.lsa.eval.Main $TYPE HERE");
    		return;
    	}
		
		System.out.println(AstUtils.e_val(expr).doubleValue());
    }

	private static String readLine() throws IOException {
		return new BufferedReader(new InputStreamReader(System.in)).readLine();
	}
}