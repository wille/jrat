package pro.jrat.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import pro.jrat.api.commands.Commands;
import pro.jrat.api.events.OnDisableEvent;
import pro.jrat.client.commands.DefaultCommands;
import pro.jrat.client.extensions.Plugin;
import pro.jrat.client.extensions.PluginLoader;
import pro.jrat.client.io.Files;
import pro.jrat.client.settings.AbstractSettings;
import pro.jrat.client.settings.Settings;
import pro.jrat.client.settings.Statistics;
import pro.jrat.client.settings.Theme;
import pro.jrat.client.threads.ThreadCheckVersion;
import pro.jrat.client.threads.ThreadPing;
import pro.jrat.client.ui.frames.Frame;
import pro.jrat.client.ui.frames.FrameAd;
import pro.jrat.client.ui.frames.FrameEULA;
import pro.jrat.client.utils.TrayIconUtils;
import pro.jrat.common.Logger;
import pro.jrat.common.OperatingSystem;
import pro.jrat.common.Version;

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

		try {
			boolean validated = UniqueId.validate(argsContains(args, "-showhexkey"));

			if (validated) {
				trial = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
			FrameEULA frame = new FrameEULA(false);
			frame.setVisible(true);
		}

		if (!argsContains(args, "-noad") && trial) {
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
