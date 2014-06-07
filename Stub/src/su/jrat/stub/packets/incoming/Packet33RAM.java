package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.packets.outgoing.Packet24JVMMemory;

public class Packet33RAM extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet24JVMMemory());
	}

}
