package pro.jrat.extensions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pro.jrat.utils.ClassUtils;



public class PluginLoader {

	public static List<Plugin> plugins = new ArrayList<Plugin>();

	public static void loadPlugins() throws Exception {
		plugins.clear();
		File folder = new File("plugins/");
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
	
	public static void loadAPI() throws Exception {
		ClassUtils.addToClassPath(new File("files/API.jar"));
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