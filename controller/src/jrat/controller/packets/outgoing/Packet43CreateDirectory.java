package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet43CreateDirectory extends AbstractOutgoingPacket {

	private String dir;
	private String name;

	public Packet43CreateDirectory(String dir, String name) {
		this.dir = dir;
		this.name = name;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(dir);
		slave.writeLine(name);
	}

	@Override
	public short getPacketId() {
		return 43;
	}

}
