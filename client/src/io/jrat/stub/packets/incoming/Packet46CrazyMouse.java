package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.utils.ScreenUtils;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

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
