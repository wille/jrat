package io.jrat.client.packets.incoming;

import io.jrat.client.Monitor;
import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet61InitMonitors extends AbstractIncomingPacket {

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
