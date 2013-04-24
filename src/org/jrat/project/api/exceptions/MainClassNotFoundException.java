package org.jrat.project.api.exceptions;


@SuppressWarnings("serial")
public class MainClassNotFoundException extends Exception {

	public MainClassNotFoundException(Throwable t) {
		super(t);
	}

	public MainClassNotFoundException() {
		super();
	}

}
