package com.redpois0n.exceptions;


@SuppressWarnings("serial")
public class CloseException extends Exception {

	public CloseException(String msg) {
		super("Closed socket: " + msg);
	}

}
