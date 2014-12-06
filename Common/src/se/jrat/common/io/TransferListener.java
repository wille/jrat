package se.jrat.common.io;

public abstract interface TransferListener {

	public abstract void transferred(long now, long bytesTotal, long totalBytes);
}
