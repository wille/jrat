package jrat.client.packets.outgoing;

import jrat.client.Connection;

import java.io.File;


public class Packet17InitDrives extends AbstractOutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		File[] drives = File.listRoots();

        con.writeInt(drives.length);
		
		for (File drive : drives) {
            con.writeLine(drive.getAbsolutePath());
            con.writeShort((short) (drive.getTotalSpace() / 1024L / 1024L / 1024L));
            con.writeShort((short) (drive.getFreeSpace() / 1024L / 1024L / 1024L));
            con.writeShort((short) (drive.getUsableSpace() / 1024L / 1024L / 1024L));
		}
	}

	@Override
	public short getPacketId() {
		return (byte) 17;
	}

}
