package io.jrat.client.extensions;

import io.jrat.client.Constants;
import io.jrat.client.Globals;
import io.jrat.client.net.WebRequest;
import io.jrat.client.ui.renderers.table.PluginsTableRenderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class OnlinePlugin {

	private String name;
	private String version;
	private String builtFor;
	private String description;
	private String author;
	private boolean downloadable;
	private ImageIcon icon;

	public OnlinePlugin(String name, String version, String builtFor, String description, String author, boolean downloadable) {
		this.name = name;
		this.version = version;
		this.builtFor = builtFor;
		this.description = description;
		this.author = author;
		this.downloadable = downloadable;
	}

	public ImageIcon getIcon() {
		if (icon == null) {
			try {
				icon = new ImageIcon(ImageIO.read(WebRequest.getInputStream(Constants.HOST + "/plugins/" + getName() + "/icon.png")));
			} catch (Exception e) {
				e.printStackTrace();
				icon = PluginsTableRenderer.PLUGIN_ICON;
			}
		}

		return icon;
	}
	
    public boolean isInstalled() {
        return new File(Globals.getPluginDirectory(), getName() + ".jar").exists();
    }

    public File getDirectory() {
        return new File(Globals.getPluginDirectory(), getName());
    }

    public File getJar() {
        return new File(Globals.getPluginDirectory(), getName() + ".jar");
    }

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name.replace(" ", "");
	}

	public String getDisplayName() {
		return name;
	}

	public String getBuiltFor() {
		return builtFor;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}

	public boolean isDownloadable() {
		return downloadable;
	}

	public static List<OnlinePlugin> getAvailablePlugins() throws Exception {
		List<OnlinePlugin> plugins = new ArrayList<OnlinePlugin>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/plugins/plugins.php")));
		String line;

		while ((line = reader.readLine()) != null) {
			String version = reader.readLine();
			String builtFor = reader.readLine();
			String description = reader.readLine();
			String author = reader.readLine();
			boolean downloadable = reader.readLine().trim().equalsIgnoreCase("true");

			OnlinePlugin plugin = new OnlinePlugin(line, version, builtFor, description, author, downloadable);

			plugins.add(plugin);
		}

		reader.close();

		return plugins;
	}

}
