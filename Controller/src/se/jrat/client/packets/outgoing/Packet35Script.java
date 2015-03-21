package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.client.Slave;
import se.jrat.common.script.Script;


public class Packet35Script extends AbstractOutgoingPacket {

	private Script type;
	private String content;

	public Packet35Script(Script type, String content) {
		this.type = type;
		this.content = content;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(type.getIdentifier());
		slave.writeLine(content);
	}

	@Override
	public byte getPacketId() {
		return 35;
	}

}
