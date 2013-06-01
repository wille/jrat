package com.redpois0n.listeners;

import java.util.LinkedHashMap;

import com.redpois0n.BuildStatus;

public abstract class BuildListener {

	public abstract void done(String msg);

	public abstract void fail(String message);

	public abstract void start();

	public abstract void reportProgress(int val, String msg, BuildStatus status);

	public abstract LinkedHashMap<String, BuildStatus> getStatuses();
 
}
