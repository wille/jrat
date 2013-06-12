package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public abstract class AbstractOutgoingPacket {
	
	public abstract void write(DataOutputStream dos, StringWriter sw) throws Exception;
	
	public abstract byte getPacketId();
	
	public final void send(DataOutputStream dos, StringWriter sw) {
		try {
			dos.writeByte(getPacketId());
			this.write(dos, sw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
