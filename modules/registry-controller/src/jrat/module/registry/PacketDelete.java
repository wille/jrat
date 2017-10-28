package jrat.module.registry;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class PacketDelete implements OutgoingPacket {

	private String path;
	private String value;
	
	public PacketDelete(String path, String value) {
		this.path = path;
		this.value = value;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(path);
		slave.writeLine(value);
	}

	@Override
	public short getPacketId() {
		return 99;
	}

}
