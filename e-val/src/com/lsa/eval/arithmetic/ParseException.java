package com.lsa.eval.arithmetic;

public final class ParseException extends RuntimeException {
    private static final long serialVersionUID = 3085941169449315385L;
	private int pos;

    public ParseException(String message, int pos) {
        super(message);
	        this.pos = pos;
    }

	public int getPosition() {
		return pos;
	}
}
