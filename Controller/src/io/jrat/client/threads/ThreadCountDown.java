package io.jrat.client.threads;

import io.jrat.client.ui.frames.DialogEula;

import javax.swing.JButton;


public class ThreadCountDown extends Thread {

	public DialogEula frame;
	public JButton button;

	public ThreadCountDown(DialogEula f, JButton b) {
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
