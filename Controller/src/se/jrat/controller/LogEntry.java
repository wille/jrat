package se.jrat.controller;

import se.jrat.controller.utils.Utils;

/**
 * Entry in {@link se.jrat.controller.ui.panels#PanelMainLog}
 */
public class LogEntry {

	private LogAction action;
	private AbstractSlave slave;
	private String info;
	private String date;

	public LogEntry(LogAction action, AbstractSlave abstractSlave, String info) {
		this.action = action;
		this.slave = abstractSlave;
		this.info = info;
		this.date = Utils.getDate();
	}

	public String getDate() {
		return date;
	}

	public LogAction getAction() {
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
