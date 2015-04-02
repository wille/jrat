package se.jrat.client.threads;

import javax.swing.Icon;
import javax.swing.JButton;

import se.jrat.client.utils.IconUtils;


public class ThreadRecordButton extends Thread {
	
	public static final Icon BIG = IconUtils.getIcon("record-big");
	public static final Icon SMALL = IconUtils.getIcon("record-small");

	private JButton button;
	private boolean running;
	
	public ThreadRecordButton(JButton button) {
		super("Record button renderer");
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
