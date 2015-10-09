package io.jrat.controller.exceptions;

@SuppressWarnings("serial")
public class InvalidSocketTypeException extends Exception {

	public InvalidSocketTypeException(String s) {
		super(s);
	}

	public InvalidSocketTypeException(Throwable t) {
		super(t);
	}

	public InvalidSocketTypeException(String s, Throwable t) {
		super(s, t);
	}

	public InvalidSocketTypeException() {

	}
}
