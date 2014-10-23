package su.jrat.client.addons;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import su.jrat.client.Constants;
import su.jrat.client.Globals;
import su.jrat.client.UniqueId;
import su.jrat.client.exceptions.MissingKeyException;
import su.jrat.client.net.WebRequest;
import su.jrat.client.ui.renderers.table.PluginsTableRenderer;
import su.jrat.common.codec.Hex;

public class OnlinePlugin {

	private String name;
	private String version;
	private String description;
	private String author;
	private ImageIcon icon;
	private String dlUrl;

	public OnlinePlugin(String name, String version, String description, String author, String dlUrl) {
		this.name = name;
		this.version = version;
		this.description = description;
		this.author = author;
		this.dlUrl = dlUrl;
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
	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}
	
	public URL getDownloadURL() throws Exception {
		URL url;
		
		if (dlUrl == null) {
			String key = "jrat";
			try {
				key = Hex.encode(UniqueId.getSystemId());
			} catch (Exception e) {
				throw new MissingKeyException("Failed to load key", e);
			}
			url = new URL(Constants.HOST + "/plugins/getplugin.php?plugin=" + getName() + "&key=" + key);
		} else {
			url = new URL(dlUrl);
		}
		
		return url;
	}

	public static List<OnlinePlugin> getAvailablePlugins() throws Exception {
		List<OnlinePlugin> plugins = new ArrayList<OnlinePlugin>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/plugins/plugins.php")));
		String line;

		while ((line = reader.readLine()) != null) {
			String version = reader.readLine();
			String description = reader.readLine();
			String author = reader.readLine();
			
			String dlUrl = reader.readLine();
			
			if (dlUrl != null && dlUrl.equals("")) {
				dlUrl = null;
			}

			OnlinePlugin plugin = new OnlinePlugin(line, version, description, author, dlUrl);

			plugins.add(plugin);
		}

		reader.close();

		return plugins;
	}

}
