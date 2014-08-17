package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;
import su.jrat.client.events.Event;
import su.jrat.client.events.Events;

public class Packet36Initialized extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		for (Event event : Events.queue.values()) {
			event.perform(slave);
		}
	}

}
