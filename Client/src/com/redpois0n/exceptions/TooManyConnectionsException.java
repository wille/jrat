package com.redpois0n.exceptions;


@SuppressWarnings("serial")
public class TooManyConnectionsException extends Exception {

	public TooManyConnectionsException(String msg) {
		super(msg);
	}

}
