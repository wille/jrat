package pro.jrat.listeners;

import java.util.LinkedHashMap;

import pro.jrat.BuildStatus;

public abstract class BuildListener {

	public abstract void done(String msg);

	public abstract void fail(String message);

	public abstract void start();

	public abstract void reportProgress(int val, String msg, BuildStatus status);

	public abstract LinkedHashMap<String, BuildStatus> getStatuses();

}
