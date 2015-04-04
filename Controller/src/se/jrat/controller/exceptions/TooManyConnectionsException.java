package se.jrat.controller.exceptions;

@SuppressWarnings("serial")
public class TooManyConnectionsException extends Exception {

	public TooManyConnectionsException(String s) {
		super(s);
	}

}
