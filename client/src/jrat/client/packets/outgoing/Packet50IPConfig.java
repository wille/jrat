package jrat.client.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet50IPConfig extends AbstractOutgoingPacket {

	private String ipconfig;

	public Packet50IPConfig(String ipconfig) {
		this.ipconfig = ipconfig;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(ipconfig);
	}

	@Override
	public short getPacketId() {
		return 50;
	}

}
