package se.jrat.controller.listeners;

import com.redpois0n.graphs.monitors.RemoteMonitor;


public abstract interface PickMonitorListener {

	public abstract void monitorPick(RemoteMonitor monitor);
	
}