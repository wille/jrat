package jrat.client.packets.outgoing;

import jrat.client.Connection;
import jrat.client.Main;

import java.io.File;


public class Packet10InitInstallPath implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		
		File file = new File(path);

        con.writeLine(file.getAbsolutePath());
	}

	@Override
	public short getPacketId() {
		return (byte) 10;
	}

}
