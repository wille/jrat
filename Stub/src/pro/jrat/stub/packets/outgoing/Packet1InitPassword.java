package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;


public class Packet1InitPassword extends AbstractOutgoingPacket {

	private String pass;
	
	public Packet1InitPassword(String pass) {
		this.pass = pass;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(pass);
	}

	@Override
	public byte getPacketId() {
		return (byte) 1;
	}

}
