package jrat.api;

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

public class Icons {
	
	public static ImageIcon getIcon(String jarname, String res) {
		return getIcon(new File(Plugin.DEFAULT_PLUGIN_DIRECTORY, jarname + ".jar"), res);
	}

	public static ImageIcon getIcon(Plugin plugin, String res) {
		return getIcon(new File(Plugin.DEFAULT_PLUGIN_DIRECTORY, plugin.getName() + ".jar"), res);
	}

	public static ImageIcon getIcon(File file, String res) {
		try {
			return new ImageIcon(new URL("jar:file:/" + file.getAbsolutePath() + "!" + res));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

}
