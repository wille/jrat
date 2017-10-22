package io.jrat.common.io;

public interface TransferListener {

	void transferred(long now, long bytesTotal, long totalBytes);
}
