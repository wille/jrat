package se.jrat.controller.packets.android.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.PacketRange;
import se.jrat.controller.addons.PluginEventHandler;
import se.jrat.controller.android.AndroidSlave;


public abstract class AbstractOutgoingAndroidPacket {

	public abstract void write(AndroidSlave slave, DataOutputStream dos) throws Exception;

	public abstract byte getPacketId();

	public synchronized final void send(AndroidSlave slave, DataOutputStream dos) {
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
