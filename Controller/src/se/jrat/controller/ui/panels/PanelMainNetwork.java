package se.jrat.controller.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import se.jrat.common.utils.DataUnits;
import se.jrat.controller.listeners.GlobalNetworkMonitorListener;
import se.jrat.controller.threads.RunnableNetworkCounter;
import se.jrat.controller.utils.TrayIconUtils;

import com.redpois0n.graphs.network.NetworkColors;
import com.redpois0n.graphs.network.NetworkGraph;

@SuppressWarnings("serial")
public class PanelMainNetwork extends JPanel {

	private NetworkGraph graph;
	
	private boolean initialized;
	
	private GraphListener listener = new GraphListener();

	public PanelMainNetwork() {
		init();
	}

	public void init() {
		RunnableNetworkCounter.addGlobalListener(listener);

		if (!initialized) {
			initialized = true;

			setBorder(new EmptyBorder(5, 5, 5, 5));
			setLayout(new BorderLayout(0, 0));
			
			graph = new NetworkGraph(new NetworkColors());

			add(graph, BorderLayout.CENTER);
		}
	}

	public void setActive(boolean b) {
		
	}
	
	private class GraphListener implements GlobalNetworkMonitorListener {
		@Override
		public void onUpdate(int out, int in) {
			graph.addValues(out, in);
			TrayIconUtils.setTitle(DataUnits.getAsString(in) + "/s down, " + DataUnits.getAsString(out) + "/s up");
		}
	}
}