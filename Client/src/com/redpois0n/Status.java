package com.redpois0n;

import java.util.HashMap;

public class Status {
	
	private static final HashMap<Integer, String> statuses = new HashMap<Integer, String>();
	
	static {
		statuses.put(2, "Executed file");
		statuses.put(3, "Downloading...");
		statuses.put(4, "Failed downloading");
		statuses.put(5, "Ready");
		statuses.put(6, "Opened messagebox");
		statuses.put(7, "Failed shutdown");
		statuses.put(8, "Starting shutdown");
		statuses.put(9, "Executed command");
    	statuses.put(10, "Made folder");

	}
	
	public static String getStatusFromID(int i) {
		return statuses.get(i);
	}
	
}
