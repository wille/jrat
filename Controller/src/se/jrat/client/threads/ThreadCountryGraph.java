package se.jrat.client.threads;

import com.redpois0n.graphs.country.CountryGraph;

public class ThreadCountryGraph extends Thread {

	private CountryGraph graph;

	public ThreadCountryGraph(CountryGraph graph) {
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
