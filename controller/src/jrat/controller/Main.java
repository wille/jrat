package jrat.controller;

import jrat.common.Logger;
import jrat.common.Version;
import jrat.common.utils.Utils;
import jrat.controller.modules.ModuleLoader;
import jrat.controller.settings.AbstractStorable;
import jrat.controller.settings.Settings;
import jrat.controller.settings.StatisticsCountry;
import jrat.controller.threads.*;
import jrat.controller.ui.dialogs.DialogEula;
import jrat.controller.ui.frames.Frame;
import jrat.controller.utils.IOUtils;
import jrat.controller.utils.TrayIconUtils;
import oslib.OperatingSystem;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
	
	public static final long START_TIME = System.currentTimeMillis();
	
	public static final String TERMINAL_PREFIX = "> ";

	public static boolean debug;
	public static boolean hideTitle;
	public static boolean headless;

	public static final List<AbstractSlave> connections = new ArrayList<>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {	
		System.out.println("jRAT " + Version.getVersion() + " " + DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()) + " " + System.getProperty("java.version"));
		
		debug = argsContains(args, "--debug");

		if (argsContains(args, "--genkey")) {
			Logger.log("Generating key");
			File file = Globals.getKeyFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(License.generateBinary());
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

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
			if (!argsContains(args, "--nomenubar")) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
			}
			
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", Constants.NAME);
		}
		
		Globals.mkdirs();

		Settings.getGlobal().load();

		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			Logger.warn("Could not set native theme, setting default");
			ex.printStackTrace();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		try {
			boolean validated = License.validate();

			if (!validated) {
				throw new Exception();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Failed authentication: " + ex.getMessage(), Constants.NAME, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}

		System.setProperty("jrat.theme", "true");
		System.setProperty("jrat.version", Version.getVersion());

		try {
			StatisticsCountry.getGlobal().load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		new ThreadPing().start();
		new ThreadSaveCycle().start();

        ModuleLoader.loadAll();

		Logger.log("Loading tray icon...");
		TrayIconUtils.initialize();

		if (Globals.getLockFile().exists()) {
			Logger.log("Lockfile already exists, we did not exit correctly");
		} else {
			Logger.log("Writing lockfile...");
			Globals.getLockFile().createNewFile();
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));
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
