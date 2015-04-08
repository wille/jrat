package se.jrat.controller.threads;

import com.redpois0n.graphs.graph.Graph;

public class ThreadGraph extends Thread {

	private Graph graph;

	public ThreadGraph(Graph graph) {
		super("Graph thread");
		this.graph = graph;
	}

	@Override
	public void run() {
		while (graph.isActive()) {
			graph.repaint();

			try {
				Thread.sleep(100L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static enum GraphMethod {
		TOTAL,
		UNIQUE;
	}
}