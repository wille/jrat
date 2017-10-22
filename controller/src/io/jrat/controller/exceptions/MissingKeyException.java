package io.jrat.controller.exceptions;

@SuppressWarnings("serial")
public class MissingKeyException extends Exception {

	public MissingKeyException(String s) {
		super(s);
	}

	public MissingKeyException(Throwable t) {
		super(t);
	}

	public MissingKeyException(String s, Throwable t) {
		super(s, t);
	}
}
