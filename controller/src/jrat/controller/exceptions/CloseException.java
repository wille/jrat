package jrat.controller.exceptions;

@SuppressWarnings("serial")
public class CloseException extends Exception {

	public CloseException(String s) {
		super("Closed socket: " + s);
	}

}
