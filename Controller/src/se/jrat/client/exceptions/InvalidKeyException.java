package se.jrat.client.exceptions;

@SuppressWarnings("serial")
public class InvalidKeyException extends Exception {

	public InvalidKeyException(String s) {
		super(s);
	}

	public InvalidKeyException(Throwable t) {
		super(t);
	}

	public InvalidKeyException(String s, Throwable t) {
		super(s, t);
	}
}
