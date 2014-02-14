package io.jrat.common.exceptions;

@SuppressWarnings("serial")
public class PacketException extends Exception {

	public PacketException(String s) {
		super(s);
	}

	public PacketException(Throwable t) {
		super(t);
	}

	public PacketException(String s, Throwable t) {
		super(s, t);
	}
}
