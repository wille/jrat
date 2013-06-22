package pro.jrat.exceptions;

@SuppressWarnings("serial")
public class RequestNotAllowedException extends Exception {

	public RequestNotAllowedException(String s) {
		super("Not allowed to request URL: " + s);
	}
}
