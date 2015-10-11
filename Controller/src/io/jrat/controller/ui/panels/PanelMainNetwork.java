package io.jrat.controller.ui.panels;

import io.jrat.common.utils.DataUnits;
import io.jrat.controller.listeners.GlobalNetworkMonitorListener;
import io.jrat.controller.threads.NetworkCounter;
import io.jrat.controller.utils.TrayIconUtils;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		NetworkCounter.addGlobalListener(listener);

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
		public void onUpdate(int in, int out) {
			graph.addValues(in, out);
			TrayIconUtils.setToolTip(DataUnits.getAsString(in) + "/s down, " + DataUnits.getAsString(out) + "/s up");
		}
	}
}