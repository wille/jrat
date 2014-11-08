package su.jrat.client.packets.incoming.android;

import java.io.DataInputStream;

import su.jrat.client.android.AndroidSlave;

public abstract class AbstractIncomingAndroidPacket {

	public abstract void read(AndroidSlave slave, DataInputStream dis) throws Exception;

}
