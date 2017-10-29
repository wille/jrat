package jrat.controller.ui.panels;

import graphslib.network.NetworkColors;
import graphslib.network.NetworkGraph;
import jrat.controller.listeners.GlobalNetworkMonitorListener;
import jrat.controller.threads.NetworkCounter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

    private class GraphListener implements GlobalNetworkMonitorListener {
		@Override
		public void onUpdate(int in, int out) {
			graph.addValues(in, out);
		}
	}
}