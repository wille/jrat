package jrat.client.packets.outgoing;

import jrat.client.Configuration;
import jrat.client.Connection;


public class Packet11InitInstallationDate implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		String date = Configuration.getInstallationDate();
		
		if (date == null) {
			date = "Unknown";
		}

        con.writeLine(date);
	}

	@Override
	public short getPacketId() {
		return (byte) 11;
	}

}
