package pro.jrat.exceptions;

@SuppressWarnings("serial")
public class ControlPanelLoadException extends Exception {

	public ControlPanelLoadException(String s) {
		super(s);
	}

	public ControlPanelLoadException(Throwable t) {
		super(t);
	}

	public ControlPanelLoadException(String s, Throwable t) {
		super(s, t);
	}
}
