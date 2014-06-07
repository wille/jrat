package su.jrat.client.exceptions;

@SuppressWarnings("serial")
public class TooManyConnectionsException extends Exception {

	public TooManyConnectionsException(String s) {
		super(s);
	}

}
