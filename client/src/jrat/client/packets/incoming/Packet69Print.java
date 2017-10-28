package jrat.client.packets.incoming;

import jrat.client.Connection;

import javax.swing.*;


public class Packet69Print implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String toprint = con.readLine();
		JTextPane c = new JTextPane();
		c.setText(toprint);
		c.print();
	}

}
