package pro.jrat.api.exceptions;

@SuppressWarnings("serial")
public class CommandNotFoundException extends Exception {

	public CommandNotFoundException(String s) {
		super(s);
	}

	public CommandNotFoundException(Throwable t) {
		super(t);
	}

	public CommandNotFoundException(String s, Throwable t) {
		super(s, t);
	}
}
