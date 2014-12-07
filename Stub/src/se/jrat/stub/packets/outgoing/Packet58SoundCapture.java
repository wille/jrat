package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.Connection;


public class Packet58SoundCapture extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 58;
	}

}