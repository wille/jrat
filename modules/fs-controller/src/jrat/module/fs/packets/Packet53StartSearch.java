package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet53StartSearch implements OutgoingPacket {

	private String searchRoot;
	private String pattern;
	private boolean pathContains;

	public Packet53StartSearch(String searchRoot, String pattern, boolean pathContains) {
		this.searchRoot = searchRoot;
		this.pattern = pattern;
		this.pathContains = pathContains;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(searchRoot);
		slave.writeLine(pattern);
		slave.writeBoolean(pathContains);
	}

	@Override
	public short getPacketId() {
		return 53;
	}

}
