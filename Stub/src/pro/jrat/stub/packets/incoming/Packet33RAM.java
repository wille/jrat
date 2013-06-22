package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet24JVMMemory;

public class Packet33RAM extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet24JVMMemory());
	}

}
