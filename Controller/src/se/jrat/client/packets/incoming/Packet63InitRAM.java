package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.Utils;


public class Packet63InitRAM extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int ram = slave.readInt();
		slave.setMemory(ram);
	}

}
