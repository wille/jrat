package io.jrat.controller.threads;

import com.redpois0n.graphs.graph.Graph;


public class ThreadGraph extends Thread {

	private Graph graph;

	public ThreadGraph(Graph countryGraph) {
		super("Graph thread");
		this.graph = countryGraph;
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
}
