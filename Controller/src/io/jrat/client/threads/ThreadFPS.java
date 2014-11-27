package io.jrat.client.threads;

public abstract class ThreadFPS extends Thread {

	private boolean running = true;
	private volatile int fps;

	public void increase() {
		fps++;
	}

	public void stopRunning() {
		running = false;
	}

	public void run() {
		try {
			while (running) {
				fps = 0;
				Thread.sleep(1000L);
				onUpdate(fps);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void onUpdate(int fps);
}
