package com.redpois0n.common.io;

public abstract interface TransferListener {

	public abstract void transferred(long now, long bytesTotal, long totalBytes);
}
