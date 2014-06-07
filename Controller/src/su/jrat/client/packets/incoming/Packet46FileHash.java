package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import javax.swing.JOptionPane;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.FrameRemoteFiles;


public class Packet46FileHash extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		final String md5 = slave.readLine();
		final String sha1 = slave.readLine();

		FrameRemoteFiles frame = FrameRemoteFiles.instances.get(slave);
		if (frame != null) {
			if (frame.waitingForMd5) {
				new Thread() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, "File MD5:\n\n" + md5 + "\n\n\nFile SHA1:\n\n" + sha1, "Hash", JOptionPane.INFORMATION_MESSAGE);
					}
				}.start();
				frame.waitingForMd5 = false;
			}
		}
	}

}
