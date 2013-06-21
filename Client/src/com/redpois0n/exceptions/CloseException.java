package com.redpois0n.exceptions;


@SuppressWarnings("serial")
public class CloseException extends Exception {

	public CloseException(String s) {
		super("Closed socket: " + s);
	}

}
