package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

public class Packet69Print extends AbstractOutgoingPacket {

	private String text;

	public Packet69Print(String text) {
		this.text = text;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(text);
	}

	@Override
	public byte getPacketId() {
		return 69;
	}

}
