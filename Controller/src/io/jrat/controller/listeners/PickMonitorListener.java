package io.jrat.controller.listeners;

import graphslib.monitors.RemoteMonitor;



public abstract interface PickMonitorListener {

	public abstract void monitorPick(RemoteMonitor monitor);
	
}
