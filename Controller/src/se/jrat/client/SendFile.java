package se.jrat.client;

import java.io.File;

import se.jrat.client.packets.outgoing.Packet42ServerUploadFile;
import se.jrat.client.ui.frames.FrameFileTransfer;
import se.jrat.client.ui.frames.FrameRemoteFiles;


public class SendFile {

	public static void sendFile(Slave slave, String file, String destdir) {
		sendFile(slave, new File(file), destdir);
	}

	public static void sendFile(final Slave slave, final File file, String destdir) {
		final FrameFileTransfer frame = FrameFileTransfer.instance;
		final FrameRemoteFiles frame2 = FrameRemoteFiles.instances.get(slave);

		if (frame2 != null) {
			frame2.start();
		}

		try {
			slave.addToSendQueue(new Packet42ServerUploadFile(file, destdir + slave.getFileSeparator() + file.getName()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
