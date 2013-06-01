package com.redpois0n.exceptions;

@SuppressWarnings("serial")
public class RequestNotAllowedException extends Exception {

	public RequestNotAllowedException(String str) {
		super("Not allowed to request: " + str);
	}
}
