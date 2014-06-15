package su.jrat.client.threads;

import su.jrat.client.Slave;
import su.jrat.client.packets.outgoing.AbstractOutgoingPacket;

public class ThreadDelayedCommand extends Thread {

	private Slave slave;
	private AbstractOutgoingPacket packet;
	private long ms;

	private ThreadDelayedCommand(Slave slave, AbstractOutgoingPacket packet, long ms) {
		super("Delayed thread");
		this.slave = slave;
		this.packet = packet;
		this.ms = ms;
	}

	public void run() {
		try {
			Thread.sleep(ms);
			slave.addToSendQueue(packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ThreadDelayedCommand start(Slave slave, AbstractOutgoingPacket packet, long ms) {
		ThreadDelayedCommand cmd = new ThreadDelayedCommand(slave, packet, ms);
		cmd.start();
		return cmd;
	}

}