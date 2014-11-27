package io.jrat.client.threads;

import io.jrat.client.utils.IconUtils;

import javax.swing.Icon;
import javax.swing.JButton;


public class ThreadRecordButton extends Thread {
	
	public static final Icon BIG = IconUtils.getIcon("record_big");
	public static final Icon SMALL = IconUtils.getIcon("record_small");

	private JButton button;
	private boolean running;
	
	public ThreadRecordButton(JButton button) {
		this.button = button;
	}
	
	@Override
	public void run() {
		running = true;
		try {
			while (running) {
				button.setIcon(BIG);
				button.revalidate();
				Thread.sleep(500L);
				button.setIcon(SMALL);
				button.revalidate();
				Thread.sleep(500L);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void stopRunning() {
		this.running = false;
	}

}
