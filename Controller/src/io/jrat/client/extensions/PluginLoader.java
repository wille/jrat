package io.jrat.client.extensions;

import io.jrat.client.Globals;
import io.jrat.client.utils.ClassUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PluginLoader {

	public static List<Plugin> plugins = new ArrayList<Plugin>();

	public static void loadPlugins() throws Exception {
		plugins.clear();
		File folder = Globals.getPluginDirectory();
		folder.mkdirs();
		File[] pluginsl = folder.listFiles();
		for (File file : pluginsl) {
			if (file.isFile() && file.getName().endsWith(".jar")) {
				try {
					new Plugin(file.getAbsolutePath());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void loadLibs() throws Exception {
		File dir = Globals.getLibDirectory();
		for (File file : dir.listFiles()) {
			ClassUtils.addToClassPath(file);
		}
	}

	public static void register(Plugin plugin) {
		plugins.remove(plugin);
		plugins.add(plugin);
	}

	public static Plugin getPlugin(String name) {
		for (Plugin plugin : plugins) {
			if (plugin.getName().equalsIgnoreCase(name)) {
				return plugin;
			}
		}

		return null;
	}

}
