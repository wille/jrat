package jrat.client.exceptions;

@SuppressWarnings("serial")
public class InvalidResponseException extends Exception {

	public InvalidResponseException() {

	}

	public InvalidResponseException(String arg0) {
		super(arg0);
	}

	public InvalidResponseException(Throwable arg0) {
		super(arg0);
	}

	public InvalidResponseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidResponseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
