package su.jrat.client.packets.android.outgoing;

import java.io.DataOutputStream;

import su.jrat.client.addons.PluginEventHandler;
import su.jrat.client.android.AndroidSlave;
import su.jrat.common.PacketRange;


public abstract class AbstractOutgoingAndroidPacket {

	public abstract void write(AndroidSlave slave, DataOutputStream dos) throws Exception;

	public abstract byte getPacketId();

	public synchronized final void send(AndroidSlave slave, DataOutputStream dos) {
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
