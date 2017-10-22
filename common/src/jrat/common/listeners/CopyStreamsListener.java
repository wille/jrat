package jrat.common.listeners;

public interface CopyStreamsListener {

	void chunk(long current, long total, int percent);
}
