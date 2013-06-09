package com.redpois0n;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import org.jrat.project.api.events.OnDisableEvent;

import com.redpois0n.common.OperatingSystem;
import com.redpois0n.common.Version;
import com.redpois0n.io.Files;
import com.redpois0n.plugins.Plugin;
import com.redpois0n.plugins.PluginLoader;
import com.redpois0n.settings.AbstractSettings;
import com.redpois0n.settings.Settings;
import com.redpois0n.settings.Theme;
import com.redpois0n.threads.ThreadCheckVersion;
import com.redpois0n.threads.ThreadPing;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.ui.frames.FrameAd;
import com.redpois0n.ui.frames.FrameEULA;
import com.redpois0n.utils.TrayIconUtils;


public class Main {

	public static final List<Slave> connections = new ArrayList<Slave>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {	
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		System.setProperty("jrat.dir", System.getProperty("user.dir"));
		System.setProperty("jrat.version", Version.getVersion());
		
		
		try {
			Theme.getGlobal().load();
			UIManager.setLookAndFeel(Theme.getGlobal().getTheme());
		} catch (Exception ex) {
			Main.debug("Could not use look and feel, setting default");
			ex.printStackTrace();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		try {
			PluginLoader.loadAPI();
			PluginLoader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		instance = new Frame();
		
		AbstractSettings.loadAllGlobals();

		boolean showEULA = Settings.getGlobal().getBoolean("showeula");
		if (!showEULA) {
			FrameEULA frame = new FrameEULA(false);
			frame.setVisible(true);
		}
		
		if (!argsContains(args, "-noad")) {
			FrameAd frame = new FrameAd();
			frame.setVisible(true);
		}
		
		Files.getSettings().mkdirs();
		Files.getFiles().mkdirs();
			
		new ThreadCheckVersion().start();
		new ThreadPing().start();
		
		instance.setVisible(true);
		
		TrayIconUtils.initialize();
		Sound.initialize();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				OnDisableEvent event = new OnDisableEvent();
				for (Plugin p : PluginLoader.plugins) {
					try {
						p.getMethods().get("ondisable").invoke(p.getInstance(), new Object[] { event });
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				AbstractSettings.saveAllGlobals();
				
				try {
					Theme.getGlobal().save();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}));
	}
	
	public static boolean argsContains(String[] args, String s) {
		for (String str : args) {
			if (str.equalsIgnoreCase(s)) {
				return true;
			}
		}
		
		return false;
	}

	public static String debug(Object s) {
		if (s == null) {
			s = "null";
		}
		System.out.println(s.toString());
		return s.toString();
	}

	public static String formatTitle() {
		return "jRAT [" + connections.size() + "] BETA " + Version.getVersion();
	}

}
