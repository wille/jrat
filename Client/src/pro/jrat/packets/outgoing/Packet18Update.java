package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;


public class Packet18Update extends AbstractOutgoingPacket {

	private String url;
	
	public Packet18Update(String url) {
		this.url = url;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
	}

	@Override
	public byte getPacketId() {
		return 18;
	}

}
