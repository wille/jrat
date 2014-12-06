package se.jrat.common.listeners;

public abstract interface CopyStreamsListener {

	public abstract void chunk(long current, long total, int percent);
}
