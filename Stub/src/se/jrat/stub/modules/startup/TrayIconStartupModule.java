package se.jrat.stub.modules.startup;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.Map;

import javax.swing.ImageIcon;

import se.jrat.stub.Configuration;
import se.jrat.stub.Main;

public class TrayIconStartupModule extends StartupModule {
	
	public TrayIconStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		if (Boolean.parseBoolean(Configuration.getConfig().get("ti"))) {
			if (SystemTray.isSupported()) {
				try {
					SystemTray tray = SystemTray.getSystemTray();
					Configuration.icon = new TrayIcon(new ImageIcon(Main.class.getResource("/icon.png")).getImage(), Configuration.getConfig().get("tititle"), null);
					tray.add(Configuration.icon);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
